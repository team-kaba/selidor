package pw.itr0.selidor.type.test;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.type.TypedString;

@ExtendWith(SoftAssertionsExtension.class)
class TypedStringTest {

  @Test
  void equality(SoftAssertions s) {
    final String string = "this is a title";
    final Title title = new Title(string);
    s.assertThat(title).isEqualTo(new Title(string));
    s.assertThat(title).isNotEqualTo(new Description(string));
  }

  @Test
  void empty(SoftAssertions s) {
    {
      final String string = "";
      final Description description = new Description(string);
      s.assertThat(description.isEmpty()).isTrue();
      s.assertThat(description.hasLength()).isFalse();
    }
    {
      final String string = "Design It!";
      final Title title = new Title(string);
      s.assertThat(title.isEmpty()).isFalse();
      s.assertThat(title.hasLength()).isTrue();
    }
  }

  @Test
  void compare(SoftAssertions s) {
    final String v1 =
        "The Feynman Lectures on Physics Vol 1: Mainly Mechanics, Radiation, and Heat";
    final Title vol1 = new Title(v1);
    final String v2 = "The Feynman Lectures on Physics Vol 2: Mainly Electromagnetism and Matter.";
    final Title vol2 = new Title(v2);
    s.assertThat(vol1).isEqualByComparingTo(new Title(v1));
    s.assertThat(vol2).isGreaterThan(vol1);
    s.assertThat(vol1).isLessThan(vol2);
  }

  @Test
  void rawValueClass(SoftAssertions s) {
    final Title title = new Title("title");
    s.assertThat(title.getRawValueClass()).isEqualTo(String.class);
  }

  private static final class Title extends TypedString<Title> {
    private Title(String value) {
      super(value);
    }
  }

  private static final class Description extends TypedString<Description> {
    private Description(String value) {
      super(value);
    }
  }
}
