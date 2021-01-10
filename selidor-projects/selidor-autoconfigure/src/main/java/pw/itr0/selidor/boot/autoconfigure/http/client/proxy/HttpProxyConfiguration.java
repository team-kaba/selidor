package pw.itr0.selidor.boot.autoconfigure.http.client.proxy;

import java.util.function.Consumer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import pw.itr0.selidor.http.client.proxy.HttpProxies;
import pw.itr0.selidor.http.client.proxy.HttpProxy;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;
import reactor.netty.transport.ProxyProvider.Proxy;
import reactor.netty.transport.ProxyProvider.TypeSpec;

abstract class HttpProxyConfiguration {

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass({WebClient.class, HttpClient.class})
  static class ReactorWebClientProxyConfiguration {
    @Bean
    @ConditionalOnBean({HttpProxies.class, ReactorResourceFactory.class})
    @ConditionalOnMissingBean(ClientHttpConnector.class)
    WebClientCustomizer webClientProxyCustomizer(
        HttpProxies proxies,
        @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // たぶん、IntelliJのバグじゃないかと。
            ReactorResourceFactory factory) {
      return proxies.primary().map(p -> applyProxy(factory, p)).orElse(b -> {});
    }

    private WebClientCustomizer applyProxy(ReactorResourceFactory factory, HttpProxy p) {
      return builder ->
          builder.clientConnector(
              new ReactorClientHttpConnector(
                  factory, client -> client.proxy(webClientProxyBuilder(p))));
    }

    private Consumer<TypeSpec> webClientProxyBuilder(HttpProxy p) {
      return typeSpec -> {
        final ProxyProvider.Builder spec =
            typeSpec
                .type(Proxy.HTTP)
                .host(p.getHost())
                .port(p.getPort())
                .nonProxyHosts(p.getNonProxyHostsRegex());
        if (p.isAuthenticationRequired()) {
          spec.httpHeaders(entries -> p.getAuthorizationHeaders().forEach(entries::add));
        }
      };
    }
  }
}
