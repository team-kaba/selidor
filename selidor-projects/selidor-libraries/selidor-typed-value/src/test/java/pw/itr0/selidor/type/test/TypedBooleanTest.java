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
    final Enabled disabled = new Enabled(false);
    final Enabled undefined = new Enabled(null);
    s.assertThat(enabled).isEqualTo(new Enabled(true));
    s.assertThat(enabled).isNotEqualTo(new Active(true));

    s.assertThat(disabled).isNotEqualTo(enabled);
    s.assertThat(disabled).isNotEqualTo(undefined);
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
      final Enabled undefined = new Enabled(null);
      s.assertThat(undefined.isTrue()).isFalse();
      s.assertThat(undefined.isFalse()).isTrue();
    }
  }

  @Test
  void compare(SoftAssertions s) {
    {
      final Enabled enabled = new Enabled(true);
      final Enabled disabled = new Enabled(false);
      final Enabled undefined = new Enabled(null);
      s.assertThat(enabled).isEqualByComparingTo(new Enabled(true));
      s.assertThat(disabled).isEqualByComparingTo(new Enabled(false));
      s.assertThat(undefined).isEqualByComparingTo(new Enabled(null));
      s.assertThat(enabled).isGreaterThan(disabled);
      s.assertThat(disabled).isLessThan(enabled);
      s.assertThat(undefined).isGreaterThan(disabled);
      s.assertThat(undefined).isGreaterThan(enabled);
    }
    {
      final Active enabled = new Active(true);
      final Active disabled = new Active(false);
      final Active undefined = new Active(null);
      s.assertThat(undefined).isLessThan(disabled);
      s.assertThat(undefined).isLessThan(enabled);
    }
  }

  private static final class Enabled extends TypedBoolean<Enabled> {

    private Enabled(Boolean value) {
      super(value, false);
    }
  }

  private static final class Active extends TypedBoolean<Active> {

    private Active(Boolean value) {
      super(value, true);
    }
  }
}
