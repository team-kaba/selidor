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
    {
      final String string = "this is a title";
      final Title title = new Title(string);
      s.assertThat(title).isEqualTo(new Title(string));
      s.assertThat(title).isNotEqualTo(new Description(string));
    }
    {
      final String string = null;
      final Title title = new Title(string);
      s.assertThat(title).isEqualTo(new Title(string));
      s.assertThat(title).isNotEqualTo(new Description(string));
      s.assertThat(title).isNotEqualTo(new Title(""));
    }
  }

  @Test
  void empty(SoftAssertions s) {
    {
      final String string = null;
      final Description description = new Description(string);
      s.assertThat(description.isEmpty()).isTrue();
      s.assertThat(description.isNotEmpty()).isFalse();
    }
    {
      final String string = "";
      final Description description = new Description(string);
      s.assertThat(description.isEmpty()).isTrue();
      s.assertThat(description.isNotEmpty()).isFalse();
    }
    {
      final String string = "Design It!";
      final Title title = new Title(string);
      s.assertThat(title.isEmpty()).isFalse();
      s.assertThat(title.isNotEmpty()).isTrue();
    }
  }

  @Test
  void blank(SoftAssertions s) {
    {
      final String value = null;
      final Title title = new Title(value);
      s.assertThat(title.isBlank()).isTrue();
      s.assertThat(title.isNotBlank()).isFalse();
    }
    {
      final String value = "";
      final Title title = new Title(value);
      s.assertThat(title.isBlank()).isTrue();
      s.assertThat(title.isNotBlank()).isFalse();
    }
    {
      final String value = "\t 　\n\r\f\r\n";
      final Title title = new Title(value);
      s.assertThat(title.isBlank()).isTrue();
      s.assertThat(title.isNotBlank()).isFalse();
    }
    {
      final String value = "\t 　\n\r\f\uD83D\uDCA3⚒️\r\n";
      final Title title = new Title(value);
      s.assertThat(title.isBlank()).isFalse();
      s.assertThat(title.isNotBlank()).isTrue();
    }
  }

  @Test
  void compare(SoftAssertions s) {
    {
      final String v1 =
          "The Feynman Lectures on Physics Vol 1: Mainly Mechanics, Radiation, and Heat";
      final Title vol1 = new Title(v1);
      final String v2 =
          "The Feynman Lectures on Physics Vol 2: Mainly Electromagnetism and Matter.";
      final Title vol2 = new Title(v2);
      final String nullValue = null;
      final Title notExist = new Title(nullValue);
      s.assertThat(vol1).isEqualByComparingTo(new Title(v1));
      s.assertThat(vol2).isGreaterThan(vol1);
      s.assertThat(vol1).isLessThan(vol2);
      s.assertThat(notExist).isEqualByComparingTo(new Title(null));
      s.assertThat(notExist).isGreaterThan(vol1);
      s.assertThat(notExist).isGreaterThan(vol2);
    }
    {
      final String v1 =
          "The Feynman Lectures on Physics Vol 1: Mainly Mechanics, Radiation, and Heat";
      final Description vol1 = new Description(v1);
      final String v2 =
          "The Feynman Lectures on Physics Vol 2: Mainly Electromagnetism and Matter.";
      final Description vol2 = new Description(v2);
      final String nullValue = null;
      final Description notExist = new Description(nullValue);
      s.assertThat(notExist).isEqualByComparingTo(new Description(null));
      s.assertThat(notExist).isLessThan(vol1);
      s.assertThat(notExist).isLessThan(vol2);
    }
  }

  private static final class Title extends TypedString<Title> {
    private Title(String value) {
      super(value, false);
    }
  }

  private static final class Description extends TypedString<Description> {
    private Description(String value) {
      super(value, true);
    }
  }
}
