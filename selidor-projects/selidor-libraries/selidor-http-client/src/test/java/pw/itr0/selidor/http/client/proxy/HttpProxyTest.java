package pw.itr0.selidor.http.client.proxy;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class HttpProxyTest {
  @Test
  void nullValue(SoftAssertions s) {
    s.assertThat(new HttpProxy(null)).isNotNull();
    s.assertThat(new HttpProxy(null, null)).isNotNull();

    final HttpProxy sut = HttpProxy.NOT_PROXIED;
    s.assertThat(sut.isRequired()).isFalse();
    s.assertThat(sut.isAuthenticationRequired()).isFalse();
  }
}
