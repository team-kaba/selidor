package pw.itr0.selidor.identifier.crid;

import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;
import pw.itr0.selidor.identifier.Id128;
import pw.itr0.selidor.identifier.IdParseFailedException;
import pw.itr0.selidor.identifier.codec.LowerCrockfordBase32;
import pw.itr0.selidor.util.ByteArrayUtil;
import pw.itr0.selidor.util.PreConditions;

/**
 * Java implementation of Chronologically sortable Random Identifier (CRID) which is binary
 * compatible with Universally Unique Lexicographically Sortable Identifier (ULID).
 *
 * <h2>Specification</h2>
 *
 * <h3>Components</h3>
 *
 * CRID is constructed by two parts, timestamp and randomness.
 *
 * <ul>
 *   <li>Timestamp is UNIX-time in milli-seconds (48bits/6bytes) which is safe until 10889 AD.
 *   <li>Randomness is 80 random bits(10bytes).
 * </ul>
 *
 * <h3>String representation</h3>
 *
 * Use Crockford's Base32 encoding with lowercase characters.
 *
 * <h2>RDBMS</h2>
 *
 * To use with PostgreSQL UUID type column, convert this id to UUID using {@link Crid#uuid()}.
 *
 * <h2>Difference from other ULID implementations</h2>
 *
 * <p>This implementation differs from ULID in string representation.
 *
 * <p><a href="https://github.com/ulid/spec#specification">ULID spec</a> says:
 *
 * <blockquote>
 *
 * Uses Crockford's base32
 *
 * </blockquote>
 *
 * But, most existing ULID implementations including the original one seem to use "32 base notation"
 * instead of "Base32 encoding", so they encode integer `1` to `"1"`.
 *
 * <p><a href="https://tools.ietf.org/html/rfc4648#section-6">Base32 Specification (RFC 4648)</a>
 * defines encoding process for input bits fewer than 40 as below:
 *
 * <blockquote>
 *
 * When fewer than 40 input bits are available in an input group, bits with value zero are added (on
 * the right) to form an integral number of 5-bit groups.
 *
 * </blockquote>
 *
 * So, using The "Extended Hex" Base 32 Alphabet encoding, 32bit integer `1` should be encoded to
 * `"0000008"`(treat 32 bits as is) or `"04"`(after removing all leading `\0`)
 *
 * <pre>
 *    integer 1
 * -> 00000000 00000000 00000000 00000001
 * -> 5-bit groups: 00000 00000 00000 00000 00000 00000 01
 * -> pad 0 to right: 00000 00000 00000 00000 00000 00000 01000
 * -> "0000008"
 *
 *    integer 1
 * -> byte 1 (down-cast)
 * -> 00000001
 * -> 5-bit groups: 00000 001
 * -> pad 0 to right: 00000 00100
 * -> "04"
 * </pre>
 *
 * <a href="https://www.crockford.com/base32.html">Crockford’s Base32</a> defines as below:
 *
 * <blockquote>
 *
 * If the bit-length of the number to be encoded is not a multiple of 5 bits, then zero-extend the
 * number to make its bit-length a multiple of 5.
 *
 * </blockquote>
 *
 * It is natural to think this is in consistent with RFC 4648, and not with "32 base notation".
 */
public final class Crid implements Id128, Comparable<Crid> {

  private static final long serialVersionUID = 7948932830425255650L;

  private static final long TIMESTAMP_OVERFLOW_MASK = 0xFFFF_0000_0000_0000L;

  @SuppressWarnings("SpellCheckingInspection")
  private static final String MAX_CRID_STRING = "zzzzzzzzzzzzzzzzzzzzzzzzzw";

  private final Instant timestamp;
  private final byte[] randomness;
  private final String base32;
  private final UUID uuid;
  private final byte[] bytes;
  private final long mostSigBits;
  private final long leastSigBits;

  Crid(long millis, byte[] randomness) throws IllegalArgumentException {
    validateTimestampMilli(millis);
    validateRandomness(randomness);

    this.timestamp = Instant.ofEpochMilli(millis);
    this.randomness = Arrays.copyOf(randomness, randomness.length);
    this.bytes = ByteArrayUtil.join(ByteArrayUtil.epochMilliToSixBytes(millis), this.randomness);
    this.base32 = LowerCrockfordBase32.encode(this.bytes);
    this.mostSigBits = ByteArrayUtil.bytesToLong(this.bytes, 0);
    this.leastSigBits = ByteArrayUtil.bytesToLong(this.bytes, 8);
    this.uuid = new UUID(this.mostSigBits, this.leastSigBits);
  }

  Crid(String base32) throws IllegalArgumentException {
    validateCridString(base32);

    this.base32 = base32;
    this.bytes = LowerCrockfordBase32.decode(base32);

    final long timestampMilli = ByteArrayUtil.sixBytesToEpochMilli(this.bytes);
    validateTimestampMilli(timestampMilli);
    this.timestamp = Instant.ofEpochMilli(timestampMilli);

    this.randomness = Arrays.copyOfRange(this.bytes, 6, this.bytes.length);
    validateRandomness(this.randomness);

    this.mostSigBits = ByteArrayUtil.bytesToLong(this.bytes, 0);
    this.leastSigBits = ByteArrayUtil.bytesToLong(this.bytes, 8);
    this.uuid = new UUID(this.mostSigBits, this.leastSigBits);
  }

  public static Crid parse(String value) {
    try {
      return new Crid(value);
    } catch (IllegalArgumentException e) {
      throw new IdParseFailedException("Failed to parse value as CRID. value=[" + value + "]", e);
    }
  }

  public static Crid from(UUID value) {
    final long msb = value.getMostSignificantBits();
    final long lsb = value.getLeastSignificantBits();
    final byte[] bytes = ByteArrayUtil.longToBytes(msb, lsb);
    return new Crid(
        ByteArrayUtil.sixBytesToEpochMilli(bytes), Arrays.copyOfRange(bytes, 6, bytes.length));
  }

  public byte[] bytes() {
    return Arrays.copyOf(bytes, bytes.length);
  }

  public Instant timestamp() {
    return timestamp;
  }

  public byte[] randomness() {
    return randomness;
  }

  /**
   * Returns {@link UUID} which is created with the same bits.
   *
   * @return UUID representation of this id
   */
  @Override
  public UUID uuid() {
    return uuid;
  }

  @Override
  public String toString() {
    return base32;
  }

  /**
   * Returns a hash code for this {@code CRID}.
   *
   * @return A hash code value for this {@code CRID}
   */
  @Override
  public int hashCode() {
    long xor = mostSigBits ^ leastSigBits;
    return ((int) (xor >> 32)) ^ (int) xor;
  }

  /**
   * Compares this object to the specified object. The result is {@code true} if and only if the
   * argument is not {@code null}, is a {@code CRID} object, has the same variant, and contains the
   * same value, bit for bit, as this {@code CRID}.
   *
   * @param obj The object to be compared
   * @return {@code true} if the objects are the same; {@code false} otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof Crid)) {
      return false;
    }
    Crid id = (Crid) obj;
    return mostSigBits == id.mostSigBits && leastSigBits == id.leastSigBits;
  }

  /**
   * Compares this CRID with the specified CRID.
   *
   * <p>The first of two CRIDs is greater than the second if the most significant field in which the
   * CRIDs differ is greater for the first CRID.
   *
   * @param val {@code CRID} to which this {@code CRID} is to be compared
   * @return -1, 0 or 1 as this {@code CRID} is less than, equal to, or greater than {@code val}
   */
  @Override
  public int compareTo(Crid val) {
    return this.mostSigBits < val.mostSigBits
        ? -1
        : (this.mostSigBits > val.mostSigBits
            ? 1
            : Long.compare(this.leastSigBits, val.leastSigBits));
  }

  private static void validateTimestampMilli(long timestamp) {
    if ((timestamp & TIMESTAMP_OVERFLOW_MASK) != 0) {
      throw new IllegalArgumentException(
          "CRID does not support timestamps after 10889-08-02T05:31:50.655Z. timestamp=["
              + timestamp
              + "L ("
              + Instant.ofEpochMilli(timestamp)
              + ")]");
    }
  }

  private void validateRandomness(byte[] randomness) {
    PreConditions.requireNonNull(randomness, () -> "CRID randomness component must not be null.");
    PreConditions.requireExactSize(
        randomness,
        10,
        () -> "CRID requires 10 random bytes. actual byte size=[" + randomness.length + "]");
  }

  private static void validateCridString(String string) {
    PreConditions.requireNonNull(string, () -> "CRID string must not be null.");
    PreConditions.requireExactSize(
        string, 26, () -> "CRID string must be exactly 26 chars long. string=[" + string + "]");
    PreConditions.requireLessThan(
        string,
        MAX_CRID_STRING,
        () -> "CRID must not exceed '" + MAX_CRID_STRING + "'. string=[" + string + "]");
  }
}
