package pw.itr0.selidor.identifier.random;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pw.itr0.selidor.identifier.IdParseFailedException;

@ExtendWith(SoftAssertionsExtension.class)
class LongIdTest {

  @ParameterizedTest(name = "{0}")
  @MethodSource("longValues")
  void testConstructors(
      @SuppressWarnings("unused") String message, Long l, byte[] bytes, SoftAssertions s) {
    final LongId sut = new LongId(l);

    s.assertThat(sut).isEqualTo(new LongId(l));
    s.assertThat(sut.longValue()).isEqualTo(l);
    s.assertThat(sut.toString()).isEqualTo(String.valueOf(l));
    s.assertThat(sut.bytes()).isEqualTo(bytes);
    s.assertThat(sut.hashCode()).isEqualTo(Long.hashCode(l));
  }

  @Test
  void equalsAndComparable(SoftAssertions s) {
    final LongId one = new LongId(1L);
    final LongId another = new LongId(2L);

    s.assertThat(one).isEqualTo(one);
    s.assertThat(one).isNotEqualTo(null);
    s.assertThat(one).isNotEqualTo(1L);
    s.assertThat(one).isNotEqualTo(another);
    s.assertThat(one).isEqualByComparingTo(one);
    s.assertThat(one).isLessThan(another);
    s.assertThat(another).isGreaterThan(one);
  }

  @Test
  void testParseLongString(SoftAssertions s) {
    final String longString = "1234567890123456789";
    s.assertThat(LongId.parse(longString)).isEqualTo(new LongId(1234567890123456789L));
  }

  @Test
  void testParseLongStringFailure() {
    assertThatExceptionOfType(IdParseFailedException.class)
        .isThrownBy(() -> LongId.parse("some invalid string"))
        .withMessageContaining("value=[some invalid string]");
  }

  @Test
  void testFromLong(SoftAssertions s) {
    final long l = 1234567890;
    s.assertThat(LongId.from(l)).isEqualTo(new LongId(l));
  }

  private static Iterable<Arguments> longValues() {
    return List.of(
        arguments("0", 0L, new byte[8]),
        arguments(
            "Long.MAX_VALUE",
            Long.MAX_VALUE,
            new byte[] {
              (byte) 0b01111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111,
              (byte) 0b11111111
            }),
        arguments(
            "Long.MIN_VALUE",
            Long.MIN_VALUE,
            new byte[] {
              (byte) 0b10000000,
              (byte) 0b00000000,
              (byte) 0b00000000,
              (byte) 0b00000000,
              (byte) 0b00000000,
              (byte) 0b00000000,
              (byte) 0b00000000,
              (byte) 0b00000000
            }));
  }
}
