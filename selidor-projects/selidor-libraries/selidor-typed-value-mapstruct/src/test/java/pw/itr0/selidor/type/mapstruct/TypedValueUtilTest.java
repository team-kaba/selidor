package pw.itr0.selidor.type.mapstruct;

import java.lang.reflect.InvocationTargetException;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.type.TypedValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneCrid;

@ExtendWith(SoftAssertionsExtension.class)
class TypedValueUtilTest {

  @Test
  @DisplayName("null")
  void nullMapping(SoftAssertions s) {
    final OneCrid mapped = TypedValueUtil.mapGeneric(null, Crid.class, OneCrid.class);
    s.assertThat(mapped).isNotNull();
    s.assertThat(mapped.isNull()).isTrue();
    s.assertThat(mapped.isNotNull()).isFalse();
  }

  @Test
  @DisplayName("NoSuchMethodException")
  void noSuchMethodException(SoftAssertions s) {
    s.assertThatThrownBy(
            () ->
                TypedValueUtil.mapGeneric("raw value", String.class, NoSuchMethodTypedValue.class))
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasCauseInstanceOf(NoSuchMethodException.class)
        .hasMessageContaining("Failed to find a constructor")
        .hasMessageContaining("java.lang.String")
        .hasMessageContaining(NoSuchMethodTypedValue.class.getCanonicalName());
  }

  @Test
  @DisplayName("InstantiationException")
  void instantiationException(SoftAssertions s) {
    s.assertThatThrownBy(
            () ->
                TypedValueUtil.mapGeneric(
                    "raw value", String.class, InstantiationFailedTypedValue.class))
        .isExactlyInstanceOf(IllegalStateException.class)
        .hasCauseInstanceOf(InstantiationException.class)
        .hasMessageContaining("Failed to instantiate class")
        .hasMessageContaining("java.lang.String")
        .hasMessageContaining(InstantiationFailedTypedValue.class.getCanonicalName());
  }

  @Test
  @DisplayName("InvocationTargetException")
  void invocationTargetException(SoftAssertions s) {
    s.assertThatThrownBy(
            () ->
                TypedValueUtil.mapGeneric(
                    "raw value", String.class, InvocationFailureTypedValue.class))
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
