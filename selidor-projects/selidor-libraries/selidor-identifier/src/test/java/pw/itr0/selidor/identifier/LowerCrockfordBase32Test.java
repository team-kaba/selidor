package pw.itr0.selidor.identifier;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.SecureRandom;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LowerCrockfordBase32Test {
  @ParameterizedTest
  @MethodSource("base32EncodeBytesToStringMapping")
  void testEncodeByte(byte[] source, String encoded) {
    final byte[] bytes = encoded.getBytes(UTF_8);

    SoftAssertions.assertSoftly(
        s -> {
          // byte[] -> byte[]
          s.assertThat(LowerCrockfordBase32.encodeToBytes(source)).isEqualTo(bytes);
          s.assertThat(LowerCrockfordBase32.decode(bytes)).isEqualTo(source);

          // byte[] -> String
          s.assertThat(LowerCrockfordBase32.encode(source)).isEqualTo(encoded);
          s.assertThat(LowerCrockfordBase32.decode(encoded)).isEqualTo(source);
        });
  }

  @ParameterizedTest
  @MethodSource("base32EncodeLongToStringMapping")
  void testEncodeLong(long source, String encoded) {
    final byte[] val = longToBytes(source);
    final byte[] bytes = encoded.getBytes(UTF_8);

    SoftAssertions.assertSoftly(
        s -> {
          // long -> byte[]
          s.assertThat(LowerCrockfordBase32.encodeToBytes(val)).isEqualTo(bytes);
          s.assertThat(LowerCrockfordBase32.decode(bytes)).isEqualTo(val);

          // long -> String
          s.assertThat(LowerCrockfordBase32.encode(val)).isEqualTo(encoded);
          s.assertThat(LowerCrockfordBase32.decode(encoded)).isEqualTo(val);
        });
  }

  @ParameterizedTest
  @MethodSource("base32DecodeStringToBytesMapping")
  void testDecode(String source, byte[] decoded) {
    final byte[] bytes = source.getBytes(UTF_8);
    SoftAssertions.assertSoftly(
        s -> {
          // byte[] -> byte[]
          s.assertThat(LowerCrockfordBase32.decode(bytes)).isEqualTo(decoded);

          // String -> byte[]
          s.assertThat(LowerCrockfordBase32.decode(source)).isEqualTo(decoded);

          // DecodeしたものはEncodeしたものと同じになるとは限らないので、逆向きのテストはしない。
          // 例: "0o" -(dec)-> {0} -(enc)-> "00" != "0o"
        });
  }

  @Test
  @Tag("slow")
  void testRandomBytes() {
    SoftAssertions.assertSoftly(
        s -> {
          final SecureRandom random = new SecureRandom();
          for (int i = 0; i < 1000; i++) {
            final byte[] r = new byte[16];
            random.nextBytes(r);

            // byte[] <-> byte[]
            final byte[] bytes = LowerCrockfordBase32.encodeToBytes(r);
            s.assertThat(bytes).hasSize(26);
            s.assertThat(LowerCrockfordBase32.decode(bytes)).isEqualTo(r);

            // byte[] <-> String
            final String string = LowerCrockfordBase32.encode(r);
            s.assertThat(string).hasSize(26);
            s.assertThat(LowerCrockfordBase32.decode(string)).isEqualTo(r);
          }
        });
  }

  @SuppressWarnings("SpellCheckingInspection")
  static Iterable<Arguments> base32EncodeLongToStringMapping() {
    return List.of(
        arguments(
            // 64bit: 0000000000000000000000000000000000000000000000000000000000000000
            0,
            // 5bits:
            //   00000_000|00_00000_0|0000_00000_00000_00000_00000_00000_00000_00000_00000_00000
            // 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30
            "0000000000000"),
        arguments(
            // 64bit: 0000000000000000000000000000000000000000000000000000000000000001
            1,
            // 5bits:
            //   00000_00000_00000_00000_00000_00000_00000_00000_00000_00000_00000_00000_00010
            // 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x32
            "0000000000002"),
        arguments(
            // 64bit: 0000000000000001000000000000000000000000000000000000000000000000
            281474976710656L,
            // 5bits:
            //   00000_00000_00000_10000_00000_00000_00000_00000_00000_00000_00000_00000_00000
            // 0x30, 0x30, 0x30, 0x47, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30
            "000g000000000"),
        arguments(
            // 64bit: 0000000000000000000000001000001000000000000000000000000000000000
            558345748480L,
            // 5bits:
            //   00000_00000_00000_00000_00001_00000_10000_00000_00000_00000_00000_00000_00000
            "000010g000000"),
        arguments(
            // 64bit: 1000000000000000000000000000000000000000000000000000000000000000
            Long.MIN_VALUE,
            // 5bits:
            //   10000_00000_00000_00000_00000_00000_00000_00000_00000_00000_00000_00000_00000
            // 0x47, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30, 0x30
            "g000000000000"),
        arguments(
            // 64bit: 0111111111111111111111111111111111111111111111111111111111111111
            Long.MAX_VALUE,
            // 5bits:
            //   01111_11111_11111_11111_11111_11111_11111_11111_11111_11111_11111_11111_11110
            // 0x46, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x59
            "fzzzzzzzzzzzy"),
        arguments(
            // 64bit: 0000000000000000111111111111111111111111111111111111111111111111
            281474976710655L,
            // 5bits:
            //   00000_00000_00000_01111_11111_11111_11111_11111_11111_11111_11111_11111_11110
            // 0x30, 0x30, 0x30, 0x46, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x5a, 0x59
            "000fzzzzzzzzy"),
        arguments(
            // 64bit: 0000000000000000000000010101011000111101111100110110010010000001
            1469918176385L,
            // 5bits:
            //   00000_00000_00000_00000_00010_10101_10001_11101_11110_01101_10010_01000_00010
            // 0x30, 0x30, 0x30, 0x30, 0x32, 0x4e, 0x48, 0x58, 0x59, 0x44, 0x4a, 0x38, 0x32
            "00002nhxydj82"));
  }

  @SuppressWarnings("SpellCheckingInspection")
  static Iterable<Arguments> base32EncodeBytesToStringMapping() {
    return List.of(
        arguments(new byte[] {0b00000_000}, "00"),
        arguments(new byte[] {0b00000_001}, "04"),
        arguments(new byte[] {0b00000_010}, "08"),
        arguments(new byte[] {0b00000_011}, "0c"),
        arguments(new byte[] {0b00000_100}, "0g"),
        arguments(new byte[] {0b00000_101}, "0m"),
        arguments(new byte[] {0b00000_110}, "0r"),
        arguments(new byte[] {0b00000_111}, "0w"),
        arguments(new byte[] {0b00001_000}, "10"),
        arguments(new byte[] {0b00001_001}, "14"),
        arguments(new byte[] {0b00001_010}, "18"),
        arguments(new byte[] {0b00001_011}, "1c"),
        arguments(new byte[] {0b00001_100}, "1g"),
        arguments(new byte[] {0b00001_101}, "1m"),
        arguments(new byte[] {0b00001_110}, "1r"),
        arguments(new byte[] {0b00001_111}, "1w"),
        arguments(new byte[] {0b00010_000}, "20"),
        arguments(new byte[] {0b00010_001}, "24"),
        arguments(new byte[] {0b00010_010}, "28"),
        arguments(new byte[] {0b00010_011}, "2c"),
        arguments(new byte[] {0b00010_100}, "2g"),
        arguments(new byte[] {0b00010_101}, "2m"),
        arguments(new byte[] {0b00010_110}, "2r"),
        arguments(new byte[] {0b00010_111}, "2w"),
        arguments(new byte[] {0b00011_000}, "30"),
        arguments(new byte[] {0b00011_001}, "34"),
        arguments(new byte[] {0b00011_010}, "38"),
        arguments(new byte[] {0b00011_011}, "3c"),
        arguments(new byte[] {0b00011_100}, "3g"),
        arguments(new byte[] {0b00011_101}, "3m"),
        arguments(new byte[] {0b00011_110}, "3r"),
        arguments(new byte[] {0b00011_111}, "3w"),
        arguments(new byte[] {0b00100_000}, "40"),
        arguments(new byte[] {0b01000_000}, "80"),
        arguments(new byte[] {(byte) 0b10000_000}, "g0"),
        arguments(new byte[] {(byte) 0b11111_111}, "zw"),
        // 32bit
        arguments(new byte[] {0b01111_111, 0b01_11111_1, 0b0111_1111, 0b0_11111_10}, "fxzqyzg"),
        // 32bit
        arguments(new byte[] {0b01111_111, 0b01_11111_1, 0b0111_1111, 0b0_11111_11}, "fxzqyzr"),
        // 40bit
        arguments(
            new byte[] {0b01010_101, 0b01_01010_1, 0b0101_0101, 0b0_10101_01, 0b010_10100},
            "anananam"),
        // 40bit
        arguments(
            new byte[] {0b01010_101, 0b01_01010_1, 0b0101_0101, 0b0_10101_01, 0b010_10101},
            "anananan"),
        // 48bit
        arguments(
            new byte[] {
              0b01010_101, 0b01_01010_1, 0b0101_0101, 0b0_10101_01, 0b010_10101, 0b01010_101
            },
            "ananananam"),
        // 48bit
        arguments(
            new byte[] {
              0b01010_101, 0b01_01010_1, 0b0101_0101, 0b0_10101_01, 0b010_10101, (byte) 0b11010_101
            },
            "anananantm"),
        // 72bit
        arguments(
            new byte[] {
              0b01110_001,
              0b01_11000_1,
              0b0111_0001,
              0b0_11100_01,
              0b011_10001,
              0b01110_001,
              0b01_11000_1,
              0b0111_0001,
              0b0_11100_01
            },
            "e5rq2wbhe5rq2w8"),
        // 72bit
        arguments(
            new byte[] {
              0b01110_001,
              0b01_11000_1,
              0b0111_0001,
              0b0_11100_01,
              0b011_10001,
              0b01110_001,
              0b01_11000_1,
              0b0111_0001,
              0b0_11100_00
            },
            "e5rq2wbhe5rq2w0"),
        // 80bit
        arguments(
            new byte[] {
              0b01101_100,
              0b01_10110_0,
              0b0110_1100,
              0b0_11011_00,
              0b011_01100,
              0b01101_100,
              0b01_10110_0,
              0b0110_1100,
              0b0_11011_00,
              0b011_01100
            },
            "dhp6rv3cdhp6rv3c"),
        // 80bit
        arguments(
            new byte[] {
              0b01101_100,
              0b01_10110_0,
              0b0110_1100,
              0b0_11011_00,
              0b011_01100,
              0b01101_100,
              0b01_10110_0,
              0b0110_1100,
              0b0_11011_00,
              0b011_01101
            },
            "dhp6rv3cdhp6rv3d"),
        // 88bit
        arguments(
            new byte[] {
              0b00101_111,
              0b00_10111_1,
              0b0010_1111,
              0b0_01011_11,
              0b001_01111,
              0b00101_111,
              0b00_10111_1,
              0b0010_1111,
              0b0_01011_11,
              0b001_01111,
              0b00101_111
            },
            "5wqjybsf5wqjybsf5w"),
        // 88bit
        arguments(
            new byte[] {
              0b00101_111,
              0b00_10111_1,
              0b0010_1111,
              0b0_01011_11,
              0b001_01111,
              0b00101_111,
              0b00_10111_1,
              0b0010_1111,
              0b0_01011_11,
              0b001_01111,
              (byte) 0b10101_111
            },
            "5wqjybsf5wqjybsfnw"),
        // statement
        arguments(
            "The quick brown fox jumps over the lazy dog.".getBytes(UTF_8),
            "ahm6a83henmp6ts0c9s6yxve41k6yy10d9tptw3k41qqcsbj41t6gs90dhgqmy90chqpebg"));
  }

  static Iterable<Arguments> base32DecodeStringToBytesMapping() {
    return List.of(
        arguments("0o", new byte[] {0b00000_000}),
        arguments("O0", new byte[] {0b00000_000}),
        arguments("oO", new byte[] {0b00000_000}),
        // 00000001_00000000 => 00001_000
        arguments("10", new byte[] {0b00001_000}),
        arguments("I0", new byte[] {0b00001_000}),
        arguments("i0", new byte[] {0b00001_000}),
        arguments("L0", new byte[] {0b00001_000}),
        arguments("l0", new byte[] {0b00001_000}),
        arguments("20", new byte[] {0b00010_000}),
        arguments("30", new byte[] {0b00011_000}),
        arguments("40", new byte[] {0b00100_000}),
        arguments("50", new byte[] {0b00101_000}),
        arguments("60", new byte[] {0b00110_000}),
        arguments("70", new byte[] {0b00111_000}),
        arguments("80", new byte[] {0b01000_000}),
        arguments("90", new byte[] {0b01001_000}),
        arguments("A0", new byte[] {0b01010_000}),
        arguments("a0", new byte[] {0b01010_000}),
        arguments("B0", new byte[] {0b01011_000}),
        arguments("b0", new byte[] {0b01011_000}),
        arguments("C0", new byte[] {0b01100_000}),
        arguments("c0", new byte[] {0b01100_000}),
        arguments("D0", new byte[] {0b01101_000}),
        arguments("d0", new byte[] {0b01101_000}),
        arguments("E0", new byte[] {0b01110_000}),
        arguments("e0", new byte[] {0b01110_000}),
        arguments("F0", new byte[] {0b01111_000}),
        arguments("f0", new byte[] {0b01111_000}),
        arguments("G0", new byte[] {(byte) 0b10000_000}),
        arguments("g0", new byte[] {(byte) 0b10000_000}),
        arguments("H0", new byte[] {(byte) 0b10001_000}),
        arguments("h0", new byte[] {(byte) 0b10001_000}),
        arguments("J0", new byte[] {(byte) 0b10010_000}),
        arguments("j0", new byte[] {(byte) 0b10010_000}),
        arguments("K0", new byte[] {(byte) 0b10011_000}),
        arguments("k0", new byte[] {(byte) 0b10011_000}),
        arguments("M0", new byte[] {(byte) 0b10100_000}),
        arguments("m0", new byte[] {(byte) 0b10100_000}),
        arguments("N0", new byte[] {(byte) 0b10101_000}),
        arguments("n0", new byte[] {(byte) 0b10101_000}),
        arguments("P0", new byte[] {(byte) 0b10110_000}),
        arguments("p0", new byte[] {(byte) 0b10110_000}),
        arguments("Q0", new byte[] {(byte) 0b10111_000}),
        arguments("q0", new byte[] {(byte) 0b10111_000}),
        arguments("R0", new byte[] {(byte) 0b11000_000}),
        arguments("r0", new byte[] {(byte) 0b11000_000}),
        arguments("S0", new byte[] {(byte) 0b11001_000}),
        arguments("s0", new byte[] {(byte) 0b11001_000}),
        arguments("T0", new byte[] {(byte) 0b11010_000}),
        arguments("t0", new byte[] {(byte) 0b11010_000}),
        arguments("V0", new byte[] {(byte) 0b11011_000}),
        arguments("v0", new byte[] {(byte) 0b11011_000}),
        arguments("W0", new byte[] {(byte) 0b11100_000}),
        arguments("w0", new byte[] {(byte) 0b11100_000}),
        arguments("X0", new byte[] {(byte) 0b11101_000}),
        arguments("x0", new byte[] {(byte) 0b11101_000}),
        arguments("Y0", new byte[] {(byte) 0b11110_000}),
        arguments("y0", new byte[] {(byte) 0b11110_000}),
        arguments("Z0", new byte[] {(byte) 0b11111_000}),
        arguments("z0", new byte[] {(byte) 0b11111_000}));
  }

  private byte[] longToBytes(long val) {
    return ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN).putLong(val).array();
  }
}
