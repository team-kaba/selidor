package pw.itr0.selidor.boot.autoconfigure.network.proxy;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import pw.itr0.selidor.network.proxy.NetworkProxy;

@ConditionalOnProperty(
    prefix = NetworkProxyConfigurationProperties.PROPERTY_PREFIX + ".autoconfigure",
    name = "enabled",
    havingValue = "true",
    matchIfMissing = true)
@Configuration
@EnableConfigurationProperties(NetworkProxyConfigurationProperties.class)
public class NetworkProxyAutoConfiguration {
  private final Logger log = LoggerFactory.getLogger(getClass());

  @Bean
  NetworkProxy httpProxy() {
    final Optional<NetworkProxy> proxy =
        List.of("http", "https").stream()
            .flatMap(this::findEnvironmentVariables)
            .map(this::parseValue)
            .flatMap(Optional::stream)
            .map(NetworkProxy::new)
            .findAny();
    return proxy.orElse(NetworkProxy.NOT_PROXIED);
  }

  private Optional<URL> parseValue(HttpProxyEnvironmentVariable envVar) {
    final String value = envVar.getValue();
    try {
      return Optional.of(new URL(value));
    } catch (MalformedURLException e) {
      log.info("Failed to parse proxy url: {}=[{}]", envVar.getName(), envVar.getName(), e);
      return Optional.empty();
    }
  }

  private Stream<HttpProxyEnvironmentVariable> findEnvironmentVariables(String scheme) {
    final Builder<HttpProxyEnvironmentVariable> builder = Stream.builder();
    final String uppercaseEnvName = scheme.toUpperCase() + "_PROXY";
    String uppercaseEnvVar = System.getenv(uppercaseEnvName);
    if (StringUtils.hasText(uppercaseEnvVar)) {
      builder.add(new HttpProxyEnvironmentVariable(uppercaseEnvName, uppercaseEnvVar));
    }
    final String lowercaseEnvName = scheme + "_proxy";
    String lowercaseEnvVar = System.getenv(lowercaseEnvName);
    if (StringUtils.hasText(lowercaseEnvVar)) {
      builder.add(new HttpProxyEnvironmentVariable(lowercaseEnvName, lowercaseEnvVar));
    }
    return builder.build();
  }

  private static final class HttpProxyEnvironmentVariable {
    private final String name;
    private final String value;

    public HttpProxyEnvironmentVariable(String name, String value) {
      this.name = name;
      this.value = value;
    }

    public String getName() {
      return name;
    }

    public String getValue() {
      return value;
    }
  }
}
