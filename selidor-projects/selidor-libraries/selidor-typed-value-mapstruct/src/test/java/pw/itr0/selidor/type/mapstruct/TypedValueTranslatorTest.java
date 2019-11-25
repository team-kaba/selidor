package pw.itr0.selidor.type.mapstruct;

import java.lang.reflect.InvocationTargetException;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pw.itr0.selidor.type.TypedValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBooleanValue;

@ExtendWith(SoftAssertionsExtension.class)
class TypedValueTranslatorTest {

  private static final TypedValueTranslator sut = new TypedValueTranslator();

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  @DisplayName("TypedBoolean")
  void typedBoolean(boolean source, SoftAssertions s) {
    final OneBooleanValue typed = sut.map(source, OneBooleanValue.class);
    s.assertThat(typed.getRawValueClass()).isEqualTo(Boolean.class);
    s.assertThat(typed.getValue()).isEqualTo(source);

    s.assertThat(sut.ident(typed)).isSameAs(typed);

    final boolean raw = sut.map(typed);
    s.assertThat(raw).isEqualTo(source);
  }

  @Test
  @DisplayName("null")
  void nullMapping(SoftAssertions s) {
    s.assertThat(sut.mapGeneric(null, OneBooleanValue.class)).isNull();

    s.assertThat(sut.map(null)).isNull();
  }

  @Test
  @DisplayName("NoSuchMethodException")
  void noSuchMethodException(SoftAssertions s) {
    s.assertThatThrownBy(() -> sut.mapGeneric("raw value", NoSuchMethodTypedValue.class))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasCauseInstanceOf(NoSuchMethodException.class)
        .hasMessageContaining("Failed to find a constructor")
        .hasMessageContaining("java.lang.String")
        .hasMessageContaining(NoSuchMethodTypedValue.class.getCanonicalName());
  }

  @Test
  @DisplayName("InstantiationException")
  void instantiationException(SoftAssertions s) {
    s.assertThatThrownBy(() -> sut.mapGeneric("raw value", InstantiationFailedTypedValue.class))
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasCauseInstanceOf(InstantiationException.class)
        .hasMessageContaining("Failed to instantiate class")
        .hasMessageContaining("java.lang.String")
        .hasMessageContaining(InstantiationFailedTypedValue.class.getCanonicalName());
  }

  @Test
  @DisplayName("InvocationTargetException")
  void invocationTargetException(SoftAssertions s) {
    s.assertThatThrownBy(() -> sut.mapGeneric("raw value", InvocationFailureTypedValue.class))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasCauseInstanceOf(InvocationTargetException.class)
        .hasRootCauseExactlyInstanceOf(RuntimeException.class)
        .hasRootCauseMessage("InvocationFailureException root cause.")
        .hasMessageContaining("Failed to instantiate class")
        .hasMessageContaining("java.lang.String")
        .hasMessageContaining(InvocationFailureTypedValue.class.getCanonicalName())
        .hasMessageContaining("[raw value]");
  }

  public static final class NoSuchMethodTypedValue extends TypedValue<String> {
    public NoSuchMethodTypedValue() {
      super("");
    }
  }

  public abstract static class InstantiationFailedTypedValue extends TypedValue<String> {
    public InstantiationFailedTypedValue(String value) {
      super(value);
    }
  }

  public static final class InvocationFailureTypedValue extends TypedValue<String> {
    public InvocationFailureTypedValue(String value) {
      super(value);
      throw new RuntimeException("InvocationFailureException root cause.");
    }
  }
}
