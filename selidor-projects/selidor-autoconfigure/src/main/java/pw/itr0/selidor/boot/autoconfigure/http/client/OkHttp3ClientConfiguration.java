package pw.itr0.selidor.boot.autoconfigure.http.client;

import java.net.ProxySelector;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pw.itr0.selidor.boot.autoconfigure.http.client.proxy.HttpProxyAutoConfiguration;
import pw.itr0.selidor.http.client.okhttp3.OkHttp3PreemptiveProxyAuthenticator;
import pw.itr0.selidor.http.client.okhttp3.ProxyAuthenticator;
import pw.itr0.selidor.http.client.proxy.HttpProxies;

abstract class OkHttp3ClientConfiguration {
  @Configuration(proxyBeanMethods = false)
  static class OkHttp3ClientBuilder {
    @Bean
    @ConditionalOnMissingBean(OkHttpClient.Builder.class)
    OkHttpClient.Builder okHttpClientBuilder(
        ObjectProvider<OkHttp3ClientBuilderCustomizer> customizers) {
      final Builder builder = new Builder();
      customizers.orderedStream().forEach(c -> c.customize(builder));
      return builder;
    }
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(RestTemplate.class)
  static class SpringRestTemplate {
    @Bean
    @ConditionalOnBean(OkHttpClient.Builder.class)
    @ConditionalOnMissingBean(ClientHttpRequestFactory.class)
    RestTemplateCustomizer okHttpRestTemplateCustomizer(OkHttpClient.Builder okHttpClientBuilder) {
      return restTemplate ->
          restTemplate.setRequestFactory(
              new OkHttp3ClientHttpRequestFactory(okHttpClientBuilder.build()));
    }
  }

  @Configuration(proxyBeanMethods = false)
  @AutoConfigureAfter(HttpProxyAutoConfiguration.class)
  @ConditionalOnClass(HttpProxies.class)
  static class ProxyAware {
    @Bean
    @ConditionalOnBean(ProxySelector.class)
    OkHttp3ClientBuilderCustomizer proxiedOkHttpClientBuilder(
        ProxySelector proxySelector, ProxyAuthenticator authenticator) {
      return builder -> builder.proxySelector(proxySelector).proxyAuthenticator(authenticator);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(HttpProxies.class)
    ProxyAuthenticator okhttp3PreemptiveProxyAuthenticator(HttpProxies proxies) {
      return new OkHttp3PreemptiveProxyAuthenticator(proxies);
    }
  }
}
