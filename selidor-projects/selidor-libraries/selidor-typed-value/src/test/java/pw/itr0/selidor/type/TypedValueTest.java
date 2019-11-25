package pw.itr0.selidor.type;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class TypedValueTest {
  @Test
  void nullValue() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(() -> new TypedValue<>(null) {})
        .withMessageContaining("`value` must not be null")
        .withMessageContaining("make variable itself be null");
  }

  @Test
  void equals() {
    final Object object = new Object();
    assertThat(new TypedObject(object)).isEqualTo(new TypedObject(object)).isNotEqualTo(null);
  }

  @Test
  void hashCodeOfValue() {
    final String string = "string";
    assertThat(new TypedObject(string).hashCode()).isEqualTo(string.hashCode());

    final BigDecimal one = BigDecimal.ONE;
    assertThat(new TypedObject(one).hashCode()).isEqualTo(one.hashCode());
  }

  private static final class TypedObject extends TypedValue<Object> {
    private TypedObject(Object value) {
      super(value);
    }
  }
}
