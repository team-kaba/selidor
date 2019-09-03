package pw.itr0.sandbox.assertj;

import static org.assertj.core.data.Percentage.withPercentage;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import pw.itr0.test.UnitTest;

@UnitTest
class ExampleAssertJTest {

  @Test
  void assertExample(SoftAssertions softly) {
    softly.assertThat(true).as("%s must be always true!", true).isTrue();
    softly.assertThat(false).as("%s must be always false!", false).isFalse();
    softly
        .assertThat(new BigDecimal("1.1"))
        .as("1.1 is close to 1 with percentage 10")
        .isCloseTo(BigDecimal.ONE, withPercentage(10));
    //noinspection ResultOfMethodCallIgnored
    softly
        .assertThatThrownBy(
            () -> URI.create("invalid url"),
            "URI#create(String) throws IllegalArgumentException if string violates RFC&nbsp;2396")
        .isExactlyInstanceOf(IllegalArgumentException.class)
        .hasCauseInstanceOf(URISyntaxException.class);
    softly
        .assertThatThrownBy(
            () -> new URI("invalid url"),
            "new URI(String) throws URISyntaxException if string violates RFC&nbsp;2396")
        .isExactlyInstanceOf(URISyntaxException.class);
  }
}
