package pw.itr0.selidor.boot.autoconfigure.http.client;

import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pw.itr0.selidor.http.client.okhttp3.OkHttp3PreemptiveProxyAuthenticator;
import pw.itr0.selidor.http.proxy.HttpProxies;
import pw.itr0.selidor.http.proxy.HttpProxy;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(OkHttpClient.class)
public class OkHttp3ClientAutoConfiguration {
  @Bean
  @ConditionalOnClass(HttpProxies.class)
  @ConditionalOnMissingBean
  OkHttpClient okHttpClient(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // たぶん、IntelliJのバグじゃないかと。
          @Autowired(required = false)
          HttpProxies proxies) {
    final OkHttpClient.Builder builder = new OkHttpClient.Builder();
    return proxies != null
        ? builder
            .proxySelector(proxies.getProxySelector())
            .proxyAuthenticator(new OkHttp3PreemptiveProxyAuthenticator(proxies))
            .build()
        : builder.build();
  }

  @Bean
  @ConditionalOnClass(HttpProxy.class)
  @ConditionalOnBean(HttpProxies.class)
  @ConditionalOnMissingBean
  OkHttp3PreemptiveProxyAuthenticator okHttp3PreemptiveProxyAuthenticator(
      @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // たぶん、IntelliJのバグじゃないかと。
          HttpProxies proxies) {
    return new OkHttp3PreemptiveProxyAuthenticator(proxies);
  }

  @Bean
  @ConditionalOnClass(RestTemplate.class)
  RestTemplateCustomizer okHttpRestTemplate(OkHttpClient okHttpClient) {
    return restTemplate ->
        restTemplate.setRequestFactory(new OkHttp3ClientHttpRequestFactory(okHttpClient));
  }
}
