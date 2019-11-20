package pw.itr0.selidor.util;

public class ByteArrayUtil {

  public static byte[] epochMilliToSixBytes(long epochMilli) {
    byte[] buffer = new byte[6];
    buffer[0] = (byte) (epochMilli >>> 40);
    buffer[1] = (byte) (epochMilli >>> 32);
    buffer[2] = (byte) (epochMilli >>> 24);
    buffer[3] = (byte) (epochMilli >>> 16);
    buffer[4] = (byte) (epochMilli >>> 8);
    buffer[5] = (byte) epochMilli;
    return buffer;
  }

  public static long sixBytesToEpochMilli(byte[] bytes) {
    return ((bytes[0] & 0xFFL) << 40)
        | ((bytes[1] & 0xFFL) << 32)
        | ((bytes[2] & 0xFFL) << 24)
        | ((bytes[3] & 0xFFL) << 16)
        | ((bytes[4] & 0xFFL) << 8)
        | (bytes[5] & 0xFFL);
  }

  public static byte[] longToBytes(long value1) {
    byte[] buffer = new byte[8];
    longToBytes(value1, buffer, 0);
    return buffer;
  }

  public static byte[] longToBytes(long value1, long value2) {
    byte[] buffer = new byte[16];
    longToBytes(value1, buffer, 0);
    longToBytes(value2, buffer, 8);
    return buffer;
  }

  public static long bytesToLong(byte[] bytes, int offset) {
    long value = 0;
    for (int i = offset; i < offset + 8; i++) {
      value = (value << 8) | (bytes[i] & 0xff);
    }
    return value;
  }

  private static void longToBytes(long value, byte[] buffer, int offset) {
    buffer[offset] = (byte) (value >>> 56);
    buffer[offset + 1] = (byte) (value >>> 48);
    buffer[offset + 2] = (byte) (value >>> 40);
    buffer[offset + 3] = (byte) (value >>> 32);
    buffer[offset + 4] = (byte) (value >>> 24);
    buffer[offset + 5] = (byte) (value >>> 16);
    buffer[offset + 6] = (byte) (value >>> 8);
    buffer[offset + 7] = (byte) value;
  }

  public static byte[] join(byte[] bytes1, byte[] bytes2) {
    final byte[] result = new byte[bytes1.length + bytes2.length];
    System.arraycopy(bytes1, 0, result, 0, bytes1.length);
    System.arraycopy(bytes2, 0, result, bytes1.length, bytes2.length);
    return result;
  }
}
