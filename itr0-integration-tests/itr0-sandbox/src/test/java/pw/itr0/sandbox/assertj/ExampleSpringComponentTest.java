package pw.itr0.sandbox.assertj;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Locale;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.ConverterNotFoundException;
import pw.itr0.test.boot.ComponentTest;

@ComponentTest
class ExampleSpringComponentTest {

  @ParameterizedTest
  @MethodSource("canConvertToBoolean")
  void assertBooleanValues(
      Object object,
      boolean expected,
      @Autowired MessageSource messageSource,
      @Autowired ConversionService conversionService,
      SoftAssertions softly) {
    softly.assertThat(conversionService.convert(object, Boolean.class)).isEqualTo(expected);
    String message = messageSource.getMessage("message1", null, Locale.getDefault());
    softly.assertThat(message).isEqualTo("This is message No.1");
  }

  private static Iterable<Arguments> canConvertToBoolean() {
    return Arrays.asList(
        Arguments.of("true", true),
        Arguments.of("false", false),
        Arguments.of("TrUe", true),
        Arguments.of("FalSe", false),
        Arguments.of("oN", true),
        Arguments.of("oFf", false),
        Arguments.of("yEs", true),
        Arguments.of("nO", false),
        Arguments.of("1", true),
        Arguments.of("0", false));
  }

  @ParameterizedTest
  @MethodSource("cannotConvertToBoolean")
  void assertNotConvertibleToBoolean(
      Object object,
      Class<?> expected,
      @Autowired ConversionService conversionService,
      SoftAssertions softly) {
    softly
        .assertThatThrownBy(() -> conversionService.convert(object, Boolean.class))
        .isExactlyInstanceOf(expected);
  }

  private static Iterable<Arguments> cannotConvertToBoolean() {
    return Arrays.asList(
        Arguments.of(BigDecimal.ONE, ConverterNotFoundException.class),
        Arguments.of(BigDecimal.ZERO, ConverterNotFoundException.class),
        Arguments.of(1, ConverterNotFoundException.class),
        Arguments.of(0, ConverterNotFoundException.class));
  }
}
