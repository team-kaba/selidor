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
  class IsEmpty {
    @Test
    void emptyValues(SoftAssertions s) {
      s.assertThat(StringUtils.isEmpty("")).isTrue();
      s.assertThat(StringUtils.isEmpty(null)).isTrue();
    }

    @Test
    void nonEmptyValues(SoftAssertions s) {
      s.assertThat(StringUtils.isEmpty(" ")).isFalse();
      s.assertThat(StringUtils.isEmpty("\t")).isFalse();
      s.assertThat(StringUtils.isEmpty("a")).isFalse();
      s.assertThat(StringUtils.isEmpty("\uD83D\uDCA3⚒️")).isFalse();
    }
  }

  @Nested
  class HasText {
    @Test
    void emptyValues(SoftAssertions s) {
      s.assertThat(StringUtils.hasText("")).isFalse();
      s.assertThat(StringUtils.hasText(null)).isFalse();
      s.assertThat(StringUtils.hasText(" ")).isFalse();
      s.assertThat(StringUtils.hasText("　")).isFalse();
      s.assertThat(StringUtils.hasText("\t")).isFalse();
    }

    @Test
    void nonEmptyValues(SoftAssertions s) {
      s.assertThat(StringUtils.hasText("a")).isTrue();
      s.assertThat(StringUtils.hasText("\uD83D\uDCA3⚒️")).isTrue();
    }
  }
}
