package pw.itr0.selidor.boot.autoconfigure.http.client.proxy;

import java.net.ProxySelector;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import pw.itr0.selidor.boot.autoconfigure.http.client.proxy.HttpProxyConfigurationProperties.ProxySetting;
import pw.itr0.selidor.boot.autoconfigure.http.client.proxy.HttpProxyConfigurationProperties.ProxySetting.BasicAuthenticationSetting;
import pw.itr0.selidor.http.client.Scheme;
import pw.itr0.selidor.http.client.proxy.EnvVarProxyLoader;
import pw.itr0.selidor.http.client.proxy.HttpProxies;
import pw.itr0.selidor.http.client.proxy.HttpProxy;
import pw.itr0.selidor.http.client.proxy.HttpProxy.Builder;

@Configuration(proxyBeanMethods = false)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureAfter(PropertyPlaceholderAutoConfiguration.class)
@ConditionalOnClass(HttpProxies.class)
@EnableConfigurationProperties(HttpProxyConfigurationProperties.class)
@Import(HttpProxyConfiguration.ReactorWebClientProxyConfiguration.class)
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
  HttpProxies httpProxies() {
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
