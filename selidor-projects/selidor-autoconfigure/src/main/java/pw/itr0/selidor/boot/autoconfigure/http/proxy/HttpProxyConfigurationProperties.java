package pw.itr0.selidor.boot.autoconfigure.http.proxy;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import pw.itr0.selidor.http.Scheme;

@ConfigurationProperties(HttpProxyConfigurationProperties.PROPERTY_PREFIX)
public class HttpProxyConfigurationProperties {
  public static final String PROPERTY_PREFIX = "selidor.http.client";

  private final boolean useDefaultProxySelector;
  private final boolean setupSystemProperties;
  private final boolean useEnvironmentVariables;
  private final List<ProxySetting> proxies;

  @ConstructorBinding
  public HttpProxyConfigurationProperties(
      @DefaultValue("true") boolean useDefaultProxySelector,
      @DefaultValue("false") boolean setupSystemProperties,
      @DefaultValue("true") boolean useEnvironmentVariables,
      List<ProxySetting> proxies) {
    this.useDefaultProxySelector = useDefaultProxySelector;
    this.setupSystemProperties = setupSystemProperties;
    this.useEnvironmentVariables = useEnvironmentVariables;
    this.proxies = Objects.requireNonNullElseGet(proxies, List::of);
  }

  public boolean isUseDefaultProxySelector() {
    return useDefaultProxySelector;
  }

  public boolean isSetupSystemProperties() {
    return setupSystemProperties;
  }

  public boolean isUseEnvironmentVariables() {
    return useEnvironmentVariables;
  }

  public List<ProxySetting> getProxies() {
    return proxies;
  }

  public static class ProxySetting {
    private final URI uri;
    private final BasicAuthenticationSetting basicAuthentication;
    private final ProxyForSetting proxyFor;

    @ConstructorBinding
    public ProxySetting(
        URI uri, BasicAuthenticationSetting basicAuthentication, ProxyForSetting proxyFor) {
      this.uri = Objects.requireNonNull(uri, "proxy uri must not be null.");
      this.basicAuthentication = basicAuthentication;
      this.proxyFor =
          Objects.requireNonNullElseGet(
              proxyFor, () -> new ProxyForSetting(Scheme.ANY, List.of(), List.of()));
    }

    public URI getUri() {
      return uri;
    }

    public BasicAuthenticationSetting getBasicAuthentication() {
      return basicAuthentication;
    }

    public ProxyForSetting getProxyFor() {
      return proxyFor;
    }

    public static class ProxyForSetting {
      private final Set<Scheme> schemes;
      private final List<String> includes;
      private final List<String> excludes;

      public ProxyForSetting(Set<Scheme> schemes, List<String> includes, List<String> excludes) {
        this.schemes = Objects.requireNonNullElse(schemes, Scheme.ANY);
        this.includes = Objects.requireNonNullElseGet(includes, List::of);
        this.excludes = Objects.requireNonNullElseGet(excludes, List::of);
      }

      public Set<Scheme> getSchemes() {
        return schemes;
      }

      public List<String> getIncludes() {
        return includes;
      }

      public List<String> getExcludes() {
        return excludes;
      }
    }

    public static class BasicAuthenticationSetting {
      private final String username;
      private final String password;

      @ConstructorBinding
      public BasicAuthenticationSetting(String username, String password) {
        this.username = username;
        this.password = password;
      }

      public String getUsername() {
        return username;
      }

      public String getPassword() {
        return password;
      }
    }
  }
}
