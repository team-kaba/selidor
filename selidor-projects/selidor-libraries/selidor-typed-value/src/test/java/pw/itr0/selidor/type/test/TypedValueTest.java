package pw.itr0.selidor.type.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import pw.itr0.selidor.type.TypedValue;

class TypedValueTest {
  @Test
  void nullValue() {
    final TypedObject typed = new TypedObject(null);
    assertThat(typed).isEqualTo(typed).isEqualTo(new TypedObject(null));
    assertThat(typed.getValue()).isNull();
  }

  @Test
  void equals() {
    final Object object = new Object();
    final TypedObject typed = new TypedObject(object);
    assertThat(typed).isEqualTo(typed);
    assertThat(typed).isEqualTo(new TypedObject(object)).isNotEqualTo(null);
  }

  @Test
  void hashCodeOfValue() {
    final String string = "string";
    assertThat(new TypedObject(string).hashCode()).isEqualTo(string.hashCode());

    final BigDecimal one = BigDecimal.ONE;
    assertThat(new TypedObject(one).hashCode()).isEqualTo(one.hashCode());
  }

  @Test
  void toStringOfValue() {
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
}
