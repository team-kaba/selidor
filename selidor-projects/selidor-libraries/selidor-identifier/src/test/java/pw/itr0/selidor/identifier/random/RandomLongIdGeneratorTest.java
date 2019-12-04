package pw.itr0.selidor.identifier.random;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.Random;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.identifier.IdParseFailedException;

@ExtendWith(SoftAssertionsExtension.class)
class RandomLongIdGeneratorTest {

  private static final long LONG = new Random().nextLong();

  private static final Random RANDOM =
      new Random() {
        @Override
        public long nextLong() {
          return LONG;
        }
      };

  @Test
  void testConstructors(SoftAssertions s) {
    {
      final RandomLongIdGenerator id = new RandomLongIdGenerator();
      s.assertThat(id.next()).isNotNull();
    }
    {
      final RandomLongIdGenerator id = new RandomLongIdGenerator(RANDOM);
      s.assertThat(id.next().longValue()).isEqualTo(LONG);
    }
  }

  @Test
  void testParseLongString(SoftAssertions s) {
    final String longString = "1234567890123456789";
    s.assertThat(new RandomLongIdGenerator().parse(longString))
        .isEqualTo(new LongId(1234567890123456789L));
  }

  @Test
  void testParseLongStringFailure() {
    assertThatExceptionOfType(IdParseFailedException.class)
        .isThrownBy(() -> new RandomLongIdGenerator().parse("some invalid string"))
        .withMessageContaining("value=[some invalid string]");
  }

  @Test
  void testNativePRNGNonBlockingNotExists() {}

  @Test
  void testFromLong(SoftAssertions s) {
    final long l = 1234567890;
    s.assertThat(new RandomLongIdGenerator().from(l)).isEqualTo(new LongId(l));
  }
}
