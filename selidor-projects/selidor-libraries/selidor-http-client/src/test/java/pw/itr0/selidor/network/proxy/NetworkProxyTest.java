package pw.itr0.selidor.network.proxy;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SoftAssertionsExtension.class)
class NetworkProxyTest {
  @Test
  void nullValue(SoftAssertions s) {
    s.assertThat(new NetworkProxy(null)).isNotNull();
    s.assertThat(new NetworkProxy(null, null)).isNotNull();

    final NetworkProxy sut = NetworkProxy.NOT_PROXIED;
    s.assertThat(sut.isRequired()).isFalse();
    s.assertThat(sut.isAuthenticationRequired()).isFalse();
  }
}
