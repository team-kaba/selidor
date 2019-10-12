package pw.itr0.selidor.identifier;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Crockford's Base32 encoder/decoder.
 *
 * <p>This class implements minimal features required to encode 80 bytes.
 *
 * <p>Not supported Crockford's Base32 feature
 *
 * <ul>
 *   <li>Encoding/decoding with check is not supported.
 *   <li>Padding is not supported.
 * </ul>
 */
abstract class CrockfordBase32 {

  private static final byte[] ENCODE_SYMBOLS = {
    (byte) /* 00000 ->  0 -> "0" */ 0x30,
    (byte) /* 00001 ->  1 -> "1" */ 0x31,
    (byte) /* 00010 ->  2 -> "2" */ 0x32,
    (byte) /* 00011 ->  3 -> "3" */ 0x33,
    (byte) /* 00100 ->  4 -> "4" */ 0x34,
    (byte) /* 00101 ->  5 -> "5" */ 0x35,
    (byte) /* 00110 ->  6 -> "6" */ 0x36,
    (byte) /* 00111 ->  7 -> "7" */ 0x37,
    (byte) /* 01000 ->  8 -> "8" */ 0x38,
    (byte) /* 01001 ->  9 -> "9" */ 0x39,
    (byte) /* 01010 -> 10 -> "A" */ 0x41,
    (byte) /* 01011 -> 11 -> "B" */ 0x42,
    (byte) /* 01100 -> 12 -> "C" */ 0x43,
    (byte) /* 01101 -> 13 -> "D" */ 0x44,
    (byte) /* 01110 -> 14 -> "E" */ 0x45,
    (byte) /* 01111 -> 15 -> "F" */ 0x46,
    (byte) /* 10000 -> 16 -> "G" */ 0x47,
    (byte) /* 10001 -> 17 -> "H" */ 0x48,
    (byte) /* 10010 -> 18 -> "J" */ 0x4a,
    (byte) /* 10011 -> 19 -> "K" */ 0x4b,
    (byte) /* 10100 -> 20 -> "M" */ 0x4d,
    (byte) /* 10101 -> 21 -> "N" */ 0x4e,
    (byte) /* 10110 -> 22 -> "P" */ 0x50,
    (byte) /* 10111 -> 23 -> "Q" */ 0x51,
    (byte) /* 11000 -> 24 -> "R" */ 0x52,
    (byte) /* 11001 -> 25 -> "S" */ 0x53,
    (byte) /* 11010 -> 26 -> "T" */ 0x54,
    (byte) /* 11011 -> 27 -> "V" */ 0x56,
    (byte) /* 11100 -> 28 -> "W" */ 0x57,
    (byte) /* 11101 -> 29 -> "X" */ 0x58,
    (byte) /* 11110 -> 30 -> "Y" */ 0x59,
    (byte) /* 11111 -> 31 -> "Z" */ 0x5a,
  };
  private static final int BITS_PER_BYTE = 8;
  private static final int BASE_BITS = 5;
  private static final int BIT_MASK = 0b00011111;
  private static final byte[] DECODE_SYMBOLS = new byte[256];

  static {
    Arrays.fill(DECODE_SYMBOLS, (byte) -1);
    for (int i = 0; i < ENCODE_SYMBOLS.length; i++) {
      final byte encoded = ENCODE_SYMBOLS[i];
      DECODE_SYMBOLS[encoded] = (byte) i;
      if (Character.isUpperCase(encoded)) {
        DECODE_SYMBOLS[Character.toLowerCase(encoded)] = (byte) i;
      }
    }

    DECODE_SYMBOLS['o'] = 0;
    DECODE_SYMBOLS['O'] = 0;
    DECODE_SYMBOLS['i'] = 1;
    DECODE_SYMBOLS['I'] = 1;
    DECODE_SYMBOLS['l'] = 1;
    DECODE_SYMBOLS['L'] = 1;
  }

  static byte[] encodeToBytes(byte[] source) {
    int length = (source.length * BITS_PER_BYTE) / BASE_BITS;
    if ((source.length * BITS_PER_BYTE) % BASE_BITS != 0) {
      length++;
    }
    final byte[] encoded = new byte[length];

    int pos = 0;
    int remaining = 0;
    int remainingBits = 0;
    for (byte b : source) {
      remainingBits += BITS_PER_BYTE;
      while (BASE_BITS <= remainingBits) {
        int i = b & 0xff;
        final int cb = remaining | (i >>> (remainingBits - BASE_BITS));
        final int idx = cb & BIT_MASK;
        encoded[pos++] = ENCODE_SYMBOLS[idx];
        remainingBits -= BASE_BITS;
        remaining = i << (BASE_BITS - remainingBits) & BIT_MASK;
      }
    }
    if (0 < remainingBits) {
      encoded[pos] = ENCODE_SYMBOLS[remaining & BIT_MASK];
    }

    return encoded;
  }

  static String encode(byte[] bytes) {
    return new String(encodeToBytes(bytes), StandardCharsets.UTF_8);
  }

  static byte[] decode(byte[] encoded) {
    int length = (encoded.length * BASE_BITS) / BITS_PER_BYTE;
    final byte[] decoded = new byte[length];

    int pos = 0;
    int remaining = 0;
    int remainingBits = 0;
    for (byte b : encoded) {
      int i = DECODE_SYMBOLS[b & 0xff];
      remainingBits += BASE_BITS;
      final int shift = BITS_PER_BYTE - remainingBits;
      int cb = (0 < shift ? i << shift : i >>> -shift) & 0xff;
      remaining |= cb;
      if (BITS_PER_BYTE <= remainingBits) {
        decoded[pos++] = (byte) remaining;
        remainingBits -= BITS_PER_BYTE;
        remaining = (i << (BITS_PER_BYTE - remainingBits)) & 0xff;
      }
    }

    return decoded;
  }

  static byte[] decode(String encoded) {
    return decode(encoded.getBytes(StandardCharsets.UTF_8));
  }
}
