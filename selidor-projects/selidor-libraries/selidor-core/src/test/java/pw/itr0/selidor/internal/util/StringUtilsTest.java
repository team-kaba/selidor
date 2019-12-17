package pw.itr0.selidor.internal.util;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@SuppressWarnings("ConstantConditions")
@ExtendWith(SoftAssertionsExtension.class)
class StringUtilsTest {
  @Nested
  class Empty {
    @Test
    void emptyValues(SoftAssertions s) {
      s.assertThat(StringUtils.isEmpty("")).isTrue();
      s.assertThat(StringUtils.isEmpty(null)).isTrue();

      s.assertThat(StringUtils.isNotEmpty("")).isFalse();
      s.assertThat(StringUtils.isNotEmpty(null)).isFalse();
    }

    @Test
    void nonEmptyValues(SoftAssertions s) {
      s.assertThat(StringUtils.isEmpty(" ")).isFalse();
      s.assertThat(StringUtils.isEmpty("\t")).isFalse();
      s.assertThat(StringUtils.isEmpty("a")).isFalse();
      s.assertThat(StringUtils.isEmpty("\uD83D\uDCA3⚒️")).isFalse();

      s.assertThat(StringUtils.isNotEmpty(" ")).isTrue();
      s.assertThat(StringUtils.isNotEmpty("\t")).isTrue();
      s.assertThat(StringUtils.isNotEmpty("a")).isTrue();
      s.assertThat(StringUtils.isNotEmpty("\uD83D\uDCA3⚒️")).isTrue();
    }
  }

  @Nested
  class Blank {
    @Test
    void blankValues(SoftAssertions s) {
      s.assertThat(StringUtils.isBlank("")).isTrue();
      s.assertThat(StringUtils.isBlank(null)).isTrue();
      s.assertThat(StringUtils.isBlank(" ")).isTrue();
      s.assertThat(StringUtils.isBlank("　")).isTrue();
      s.assertThat(StringUtils.isBlank("\t")).isTrue();
      s.assertThat(StringUtils.isBlank(" 　\t\n\r\f\r\n\r\n")).isTrue();

      s.assertThat(StringUtils.isNotBlank("")).isFalse();
      s.assertThat(StringUtils.isNotBlank(null)).isFalse();
      s.assertThat(StringUtils.isNotBlank(" ")).isFalse();
      s.assertThat(StringUtils.isNotBlank("　")).isFalse();
      s.assertThat(StringUtils.isNotBlank("\t")).isFalse();
      s.assertThat(StringUtils.isNotBlank(" 　\t\n\r\f\r\n\r\n")).isFalse();
    }

    @Test
    void nonBlankValues(SoftAssertions s) {
      s.assertThat(StringUtils.isBlank("a")).isFalse();
      s.assertThat(StringUtils.isBlank("\t 　\n\r\f\uD83D\uDCA3⚒️\r\n")).isFalse();

      s.assertThat(StringUtils.isNotBlank("a")).isTrue();
      s.assertThat(StringUtils.isNotBlank("\t 　\n\r\f\uD83D\uDCA3⚒️\r\n")).isTrue();
    }
  }
}
