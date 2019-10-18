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
abstract class LowerCrockfordBase32 {

  private static final byte[] ENCODE_SYMBOLS = {
    (byte) /* 00000 ->  0 -> "0" */ '0',
    (byte) /* 00001 ->  1 -> "1" */ '1',
    (byte) /* 00010 ->  2 -> "2" */ '2',
    (byte) /* 00011 ->  3 -> "3" */ '3',
    (byte) /* 00100 ->  4 -> "4" */ '4',
    (byte) /* 00101 ->  5 -> "5" */ '5',
    (byte) /* 00110 ->  6 -> "6" */ '6',
    (byte) /* 00111 ->  7 -> "7" */ '7',
    (byte) /* 01000 ->  8 -> "8" */ '8',
    (byte) /* 01001 ->  9 -> "9" */ '9',
    (byte) /* 01010 -> 10 -> "A" */ 'a',
    (byte) /* 01011 -> 11 -> "B" */ 'b',
    (byte) /* 01100 -> 12 -> "C" */ 'c',
    (byte) /* 01101 -> 13 -> "D" */ 'd',
    (byte) /* 01110 -> 14 -> "E" */ 'e',
    (byte) /* 01111 -> 15 -> "F" */ 'f',
    (byte) /* 10000 -> 16 -> "G" */ 'g',
    (byte) /* 10001 -> 17 -> "H" */ 'h',
    (byte) /* 10010 -> 18 -> "J" */ 'j',
    (byte) /* 10011 -> 19 -> "K" */ 'k',
    (byte) /* 10100 -> 20 -> "M" */ 'm',
    (byte) /* 10101 -> 21 -> "N" */ 'n',
    (byte) /* 10110 -> 22 -> "P" */ 'p',
    (byte) /* 10111 -> 23 -> "Q" */ 'q',
    (byte) /* 11000 -> 24 -> "R" */ 'r',
    (byte) /* 11001 -> 25 -> "S" */ 's',
    (byte) /* 11010 -> 26 -> "T" */ 't',
    (byte) /* 11011 -> 27 -> "V" */ 'v',
    (byte) /* 11100 -> 28 -> "W" */ 'w',
    (byte) /* 11101 -> 29 -> "X" */ 'x',
    (byte) /* 11110 -> 30 -> "Y" */ 'y',
    (byte) /* 11111 -> 31 -> "Z" */ 'z',
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
      if (Character.isLowerCase(encoded)) {
        DECODE_SYMBOLS[Character.toUpperCase(encoded)] = (byte) i;
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
