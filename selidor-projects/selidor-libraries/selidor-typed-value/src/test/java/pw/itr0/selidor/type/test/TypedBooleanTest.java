package pw.itr0.selidor.type.test;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.type.TypedBoolean;

@ExtendWith(SoftAssertionsExtension.class)
class TypedBooleanTest {

  @Test
  void equality(SoftAssertions s) {
    final Enabled enabled = new Enabled(true);
    s.assertThat(enabled).isEqualTo(new Enabled(true));
    s.assertThat(enabled).isNotEqualTo(new Active(true));
  }

  @Test
  void truthy(SoftAssertions s) {
    {
      final Active active = new Active(true);
      s.assertThat(active.isTrue()).isTrue();
      s.assertThat(active.isFalse()).isFalse();
    }
    {
      final Active inactive = new Active(false);
      s.assertThat(inactive.isTrue()).isFalse();
      s.assertThat(inactive.isFalse()).isTrue();
    }
    {
      final Enabled enabled = new Enabled(true);
      s.assertThat(enabled.isTrue()).isTrue();
      s.assertThat(enabled.isFalse()).isFalse();
    }
    {
      final Enabled enabled = new Enabled(false);
      s.assertThat(enabled.isTrue()).isFalse();
      s.assertThat(enabled.isFalse()).isTrue();
    }
  }

  @Test
  void compare(SoftAssertions s) {
    final Enabled enabled = new Enabled(true);
    final Enabled disabled = new Enabled(false);
    s.assertThat(enabled).isEqualByComparingTo(new Enabled(true));
    s.assertThat(disabled).isEqualByComparingTo(new Enabled(false));
    s.assertThat(enabled).isGreaterThan(disabled);
    s.assertThat(disabled).isLessThan(enabled);
  }

  private static final class Enabled extends TypedBoolean<Enabled> {

    private Enabled(boolean value) {
      super(value);
    }
  }

  private static final class Active extends TypedBoolean<Enabled> {

    private Active(boolean value) {
      super(value);
    }
  }
}
