package pw.itr0.selidor.identifier.random;

import pw.itr0.selidor.identifier.Id64;
import pw.itr0.selidor.util.ByteArrayUtil;

public final class LongId implements Id64, Comparable<LongId> {

  private final long value;

  LongId(long value) {
    this.value = value;
  }

  @Override
  public long longValue() {
    return value;
  }

  public byte[] bytes() {
    return ByteArrayUtil.longToBytes(value);
  }

  @Override
  public int hashCode() {
    return Long.hashCode(value);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Long) {
      return value == (Long) obj;
    }
    return false;
  }

  @Override
  public int compareTo(LongId o) {
    return Long.compare(value, o.value);
  }

  public String toString() {
    return Long.toString(value);
  }
}
