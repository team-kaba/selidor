package pw.itr0.selidor.type.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pw.itr0.selidor.type.TypedValue;

class TypedValueTest {
  @Test
  void nullValue() {
    final TypedObject typedNull = new TypedObject(null);
    assertThat(typedNull.getNullableValue()).isNull();
    assertThat(typedNull.getValue()).isEmpty();
    assertThat(typedNull.isNull()).isTrue();
    assertThat(typedNull.isNotNull()).isFalse();

    final TypedObject typedNotNull = new TypedObject(new Object());
    assertThat(typedNotNull.isNull()).isFalse();
    assertThat(typedNotNull.isNotNull()).isTrue();
  }

  @Test
  void equals() {
    final Object object = new Object();
    final TypedObject typed = new TypedObject(object);
    final TypedObject nullValue = new TypedObject(null);
    assertThat(typed)
        .isEqualTo(typed)
        .isEqualTo(new TypedObject(object))
        .isNotEqualTo(new TypedObject(new Object()))
        .isNotEqualTo(nullValue)
        .isNotEqualTo(null)
        .isNotEqualTo(new TypedOtherObject(object));
    assertThat(nullValue).isEqualTo(nullValue).isEqualTo(new TypedObject(null)).isNotEqualTo(typed);
  }

  @Test
  void hashCodeOfValue() {
    assertThat(new TypedObject(null).hashCode()).isEqualTo(0);

    final String string = "string";
    assertThat(new TypedObject(string).hashCode()).isEqualTo(string.hashCode());

    final BigDecimal one = BigDecimal.ONE;
    assertThat(new TypedObject(one).hashCode()).isEqualTo(one.hashCode());
  }

  @Test
  void toStringOfValue() {
    assertThat(new TypedObject(null).toString()).isEqualTo("null");

    final String string = "string";
    assertThat(new TypedObject(string).toString()).isEqualTo("string");

    final BigDecimal one = BigDecimal.ONE;
    assertThat(new TypedObject(one).toString()).isEqualTo("1");
  }

  private static final class TypedObject extends TypedValue<Object> {
    private TypedObject(Object value) {
      super(value);
    }
  }

  private static final class TypedOtherObject extends TypedValue<Object> {
    private TypedOtherObject(Object value) {
      super(value);
    }
  }
}
