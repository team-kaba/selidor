package pw.itr0.selidor.sandbox.assertj;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import pw.itr0.selidor.test.Slow;
import pw.itr0.selidor.test.UnitTest;

@Slow
@UnitTest
class ExampleSlowTest {
  @Test
  void timeoutNotExceededWithResult() {
    assertTimeout(ofSeconds(10), () -> TimeUnit.MILLISECONDS.sleep(9990));
  }
}
