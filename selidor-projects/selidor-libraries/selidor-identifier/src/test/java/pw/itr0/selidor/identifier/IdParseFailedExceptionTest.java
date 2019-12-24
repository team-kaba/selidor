package pw.itr0.selidor.identifier;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class IdParseFailedExceptionTest {
  @Test
  void constructor(SoftAssertions s) {
    {
      final IdParseFailedException sut = new IdParseFailedException("message only");
      s.assertThat(sut)
          .hasMessage("message only")
          .hasNoCause()
          .isInstanceOf(RuntimeException.class);
    }
    {
      final RuntimeException root = new RuntimeException("root cause");
      final IdParseFailedException sut = new IdParseFailedException("message and root cause", root);
      s.assertThat(sut).hasMessage("message and root cause").hasCause(root);
    }
  }
}
