package pw.itr0.selidor.boot.autoconfigure.http.client;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pw.itr0.selidor.http.client.okhttp3.OkHttp3PreemptiveProxyAuthenticator;
import pw.itr0.selidor.http.client.proxy.HttpProxies;

abstract class OkHttp3ClientConfiguration {
  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(RestTemplate.class)
  static class SpringRestTemplate {
    @Bean
    @ConditionalOnBean(OkHttpClient.class)
    @ConditionalOnMissingBean(ClientHttpRequestFactory.class)
    RestTemplateCustomizer okHttpRestTemplate(
        // @ConditionalOnBeanを付けているので問題ないはずですが、IntelliJ IDEAでは警告が出てしまうので抑止します。
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
            OkHttpClient okHttpClient) {
      return restTemplate ->
          restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(okHttpClient));
    }
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnMissingClass("pw.itr0.selidor.http.client.proxy.HttpProxies")
  static class Direct {
    @Bean
    @ConditionalOnMissingBean
    OkHttpClient okHttpClient() {
      return new OkHttpClient.Builder().build();
    }
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(HttpProxies.class)
  static class ProxyAware {
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(HttpProxies.class)
    OkHttpClient proxiedOkHttpClient(HttpProxies proxies, Authenticator authenticator) {
      return new OkHttpClient.Builder()
          .proxySelector(proxies.getProxySelector())
          .proxyAuthenticator(authenticator)
          .build();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(HttpProxies.class)
    Authenticator okhttp3PreemptiveProxyAuthenticator(HttpProxies proxies) {
      return new OkHttp3PreemptiveProxyAuthenticator(proxies);
    }

    @Bean
    @ConditionalOnMissingBean({OkHttpClient.class, HttpProxies.class})
    OkHttpClient nonProxiedOkHttpClient() {
      return new OkHttpClient.Builder().build();
    }
  }
}
