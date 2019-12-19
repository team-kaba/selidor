package pw.itr0.selidor.boot.autoconfigure.http.proxy;

import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import pw.itr0.selidor.boot.autoconfigure.http.proxy.HttpProxyConfigurationProperties.ProxySetting;
import pw.itr0.selidor.boot.autoconfigure.http.proxy.HttpProxyConfigurationProperties.ProxySetting.BasicAuthenticationSetting;
import pw.itr0.selidor.http.client.Scheme;
import pw.itr0.selidor.http.client.proxy.EnvVarProxyLoader;
import pw.itr0.selidor.http.client.proxy.HttpProxies;
import pw.itr0.selidor.http.client.proxy.HttpProxy;
import pw.itr0.selidor.http.client.proxy.HttpProxy.Builder;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.ProxyProvider;
import reactor.netty.tcp.ProxyProvider.Proxy;
import reactor.netty.tcp.ProxyProvider.TypeSpec;

@Configuration(proxyBeanMethods = false)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureAfter(PropertyPlaceholderAutoConfiguration.class)
@ConditionalOnClass(HttpProxies.class)
@EnableConfigurationProperties(HttpProxyConfigurationProperties.class)
public class HttpProxyAutoConfiguration {
  private static final List<ProxyEnvVar> PROXY_ENV_VARS =
      List.of(
          new ProxyEnvVar("http_proxy", Scheme.HTTP),
          new ProxyEnvVar("https_proxy", Scheme.HTTPS),
          new ProxyEnvVar("HTTP_PROXY", Scheme.HTTP),
          new ProxyEnvVar("HTTPS_PROXY", Scheme.HTTPS));

  private final HttpProxyConfigurationProperties properties;

  public HttpProxyAutoConfiguration(HttpProxyConfigurationProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  HttpProxies networkProxies() {
    final List<HttpProxy> proxies = new ArrayList<>(getProxiesSize());
    properties.getProxies().stream().map(this::createNetworkProxy).forEachOrdered(proxies::add);
    if (properties.isUseEnvironmentVariables()) {
      for (ProxyEnvVar env : PROXY_ENV_VARS) {
        EnvVarProxyLoader.load(env.getVariable(), env.getScheme()).map(proxies::add);
      }
    }
    return new HttpProxies(
        proxies, properties.isUseDefaultProxySelector(), properties.isSetupSystemProperties());
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(HttpProxies.class)
  ProxySelector proxySelector(HttpProxies proxies) {
    return proxies.getProxySelector();
  }

  private int getProxiesSize() {
    return properties.getProxies().size()
        + (properties.isUseEnvironmentVariables() ? PROXY_ENV_VARS.size() : 0);
  }

  @Bean
  @ConditionalOnClass({WebClient.class, HttpClient.class})
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
                factory,
                client ->
                    client.tcpConfiguration(
                        tcpClient -> tcpClient.proxy(webClientProxyBuilder(p)))));
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

  private HttpProxy createNetworkProxy(ProxySetting proxySetting) {
    final Builder builder =
        new Builder()
            .addSchemes(proxySetting.getProxyFor().getSchemes())
            .uri(proxySetting.getUri())
            .addIncludes(proxySetting.getProxyFor().getIncludes())
            .addExcludes(proxySetting.getProxyFor().getExcludes());
    final BasicAuthenticationSetting basic = proxySetting.getBasicAuthentication();
    if (basic != null) {
      builder.basicAuthentication(basic.getUsername(), basic.getPassword());
    }
    return builder.build();
  }

  private static class ProxyEnvVar {
    private final String variable;
    private final Scheme scheme;

    public ProxyEnvVar(String variable, Scheme scheme) {
      this.variable = variable;
      this.scheme = scheme;
    }

    public String getVariable() {
      return variable;
    }

    public Scheme getScheme() {
      return scheme;
    }
  }
}
