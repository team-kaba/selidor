package pw.itr0.selidor.identifier.crid;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pw.itr0.selidor.util.ByteArrayUtil;

@SuppressWarnings("SpellCheckingInspection")
class CridTest {
  @ParameterizedTest(name = "{0}")
  @MethodSource("timestampAndRandom")
  void testConstructors(
      @SuppressWarnings("unused") String message,
      long timestamp,
      byte[] randomness,
      String string) {
    final Crid sut = new Crid(timestamp, randomness);
    final Crid before = new Crid(timestamp - 1, randomness);
    final Crid after = new Crid(timestamp + 1, randomness);
    final byte[] lsbDiff = Arrays.copyOf(randomness, randomness.length);
    lsbDiff[randomness.length - 1] = (byte) (lsbDiff[randomness.length - 1] - 1);
    final Crid differentLsb = new Crid(timestamp, lsbDiff);
    final Crid copy = new Crid(string);
    final byte[] bytes = joinToBytes(timestamp, randomness);
    assertSoftly(
        s -> {
          s.assertThat(sut.timestamp()).isEqualTo(Instant.ofEpochMilli(timestamp));
          s.assertThat(sut.timestamp()).isEqualTo(copy.timestamp());
          s.assertThat(sut.randomness()).isEqualTo(randomness);
          s.assertThat(sut.randomness()).isEqualTo(copy.randomness());
          s.assertThat(sut.randomness()).isNotSameAs(randomness);
          s.assertThat(sut.bytes()).isEqualTo(bytes);
          s.assertThat(sut.bytes()).isEqualTo(copy.bytes());
          s.assertThat(sut.uuid()).isEqualTo(new UUID(msb(bytes), lsb(bytes)));
          s.assertThat(sut.uuid()).isEqualTo(copy.uuid());
          s.assertThat(sut.toString()).isEqualTo(string);
          s.assertThat(sut.toString()).isEqualTo(copy.toString());
          s.assertThat(sut.compareTo(copy)).isEqualTo(0);
          s.assertThat(sut.compareTo(before)).isGreaterThan(0);
          s.assertThat(sut.compareTo(after)).isLessThan(0);
          s.assertThat(sut).isEqualTo(sut);
          s.assertThat(sut).isEqualTo(copy);
          s.assertThat(sut).isNotEqualTo(string);
          s.assertThat(sut).isNotEqualTo(after);
          s.assertThat(sut).isNotEqualTo(differentLsb);
          s.assertThat(sut.hashCode()).isEqualTo(copy.hashCode());
          s.assertThat(sut.hashCode()).isNotEqualTo(before.hashCode());
          s.assertThat(sut.hashCode()).isNotEqualTo(after.hashCode());
        });
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("illegalTimestampAndRandomness")
  void testIllegalTimestampAndRandomness(
      @SuppressWarnings("unused") String message,
      long timestamp,
      byte[] randomness,
      String exception) {
    assertThatThrownBy(() -> new Crid(timestamp, randomness))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasNoCause()
        .hasMessageContaining(exception);
  }

  @ParameterizedTest(name = "{0}")
  @MethodSource("illegalCridString")
  void testIllegalCridString(
      @SuppressWarnings("unused") String message, String crid, String exception) {
    assertThatThrownBy(() -> new Crid(crid))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasNoCause()
        .hasMessageContaining(exception);
  }

  @SuppressWarnings("SpellCheckingInspection")
  static Iterable<Arguments> timestampAndRandom() {
    final Random random = new Random(1);
    final byte[] bytes1 = new byte[10];
    random.nextBytes(bytes1);
    return List.of(
        arguments(
            "Simple success",
            1L,
            new byte[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            "00000000040020g30g2gc1r814"),
        arguments(
            "Actual value",
            Instant.parse("2021-12-03T10:15:30Z").toEpochMilli(),
            bytes1,
            "05yqzjnht1sxa6nvv2ebg6bf1r"),
        arguments(
            "Maximum value - 1",
            0x0000_FFFF_FFFF_FFFEL,
            new byte[] {
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111
            },
            "zzzzzzzzzvzzzzzzzzzzzzzzzw"),
        arguments("Empty random bytes", 194L, new byte[10], "00000000r80000000000000000"));
  }

  static Iterable<Arguments> illegalTimestampAndRandomness() {
    return List.of(
        arguments(
            "Unsupported timestamp (boundary)",
            0x0001_0000_0000_0000L,
            new byte[10],
            "timestamp=["
                + 0x0001_0000_0000_0000L
                + "L ("
                + Instant.ofEpochMilli(0x0001_0000_0000_0000L)
                + ")"),
        arguments(
            "Unsupported timestamp (max)",
            Long.MAX_VALUE,
            new byte[10],
            "timestamp=[" + Long.MAX_VALUE + "L (" + Instant.ofEpochMilli(Long.MAX_VALUE) + ")"),
        arguments(
            "Unsupported timestamp (min)",
            Long.MIN_VALUE,
            new byte[10],
            "timestamp=[" + Long.MIN_VALUE + "L (" + Instant.ofEpochMilli(Long.MIN_VALUE) + ")"),
        arguments(
            "Too few random bytes", Instant.now().toEpochMilli(), new byte[9], "byte size=[9]"),
        arguments(
            "Too many random bytes", Instant.now().toEpochMilli(), new byte[11], "byte size=[11]"),
        arguments("Empty random bytes", Instant.now().toEpochMilli(), new byte[0], "byte size=[0]"),
        arguments(
            "null random bytes",
            Instant.now().toEpochMilli(),
            null,
            "randomness component must not be null."));
  }

  static Iterable<Arguments> illegalCridString() {
    return List.of(
        arguments("Empty", "", "string=[]"),
        arguments("null", null, "string must not be null"),
        arguments(
            "Too short",
            "0123456789012345678901234",
            "exactly 26 chars long. string=[0123456789012345678901234]"),
        arguments(
            "Too long",
            "012345678901234567890123456",
            "exactly 26 chars long. string=[012345678901234567890123456]"),
        arguments(
            "Exceeds upper limit",
            "zzzzzzzzzzzzzzzzzzzzzzzzzx",
            "must not exceed 'zzzzzzzzzzzzzzzzzzzzzzzzzw'. string=[zzzzzzzzzzzzzzzzzzzzzzzzzx]"),
        arguments(
            "Invalid single byte character",
            "0.........................",
            "char=[.] string=[0.........................]"),
        arguments(
            "Invalid two byte character",
            "012345678901234567890あいうえお",
            "char=["
                + (char) ("あ".getBytes(StandardCharsets.UTF_8)[0] & 0xff)
                + "] string=[012345678901234567890あいうえお]"),
        arguments(
            "Invalid multi byte character",
            "01234567890123456789012髙島屋",
            "char=["
                + (char) ("髙".getBytes(StandardCharsets.UTF_8)[0] & 0xff)
                + "] string=[01234567890123456789012髙島屋]"),
        arguments(
            "Timestamp overflow",
            "012345678901234567890123456",
            "string=[012345678901234567890123456]"));
  }

  private byte[] joinToBytes(long val, byte[] bytes) {
    return ByteBuffer.allocate(16)
        .order(ByteOrder.BIG_ENDIAN)
        .put(ByteArrayUtil.epochMilliToSixBytes(val))
        .put(bytes)
        .array();
  }

  private long msb(byte[] bytes) {
    return ByteBuffer.allocate(8).put(bytes, 0, 8).clear().getLong();
  }

  private long lsb(byte[] bytes) {
    return ByteBuffer.allocate(8).put(bytes, 8, 8).clear().getLong();
  }
}
