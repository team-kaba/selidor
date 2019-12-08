package pw.itr0.selidor.identifier.random;

import pw.itr0.selidor.identifier.Id64;
import pw.itr0.selidor.identifier.IdParseFailedException;
import pw.itr0.selidor.internal.util.ByteArrayUtils;

public final class LongId implements Id64, Comparable<LongId> {

  private final long value;

  LongId(long value) {
    this.value = value;
  }

  public static LongId parse(String value) {
    try {
      return new LongId(Long.parseLong(value));
    } catch (NumberFormatException e) {
      throw new IdParseFailedException("Failed to parse value as long. value=[" + value + "]", e);
    }
  }

  public static LongId from(long value) {
    return new LongId(value);
  }

  @Override
  public long longValue() {
    return value;
  }

  public byte[] bytes() {
    return ByteArrayUtils.longToBytes(value);
  }

  @Override
  public int hashCode() {
    return Long.hashCode(value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    LongId longId = (LongId) o;

    return value == longId.value;
  }

  @Override
  public int compareTo(LongId o) {
    return Long.compare(value, o.value);
  }

  public String toString() {
    return Long.toString(value);
  }
}
