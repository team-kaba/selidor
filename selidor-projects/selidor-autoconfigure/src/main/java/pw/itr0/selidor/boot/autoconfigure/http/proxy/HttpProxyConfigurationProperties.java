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
  private final ProxySetting proxy;

  @ConstructorBinding
  public HttpProxyConfigurationProperties(
      @DefaultValue("true") boolean useDefaultProxySelector,
      @DefaultValue("false") boolean setupSystemProperties,
      @DefaultValue("true") boolean useEnvironmentVariables,
      List<ProxySetting> proxies,
      ProxySetting proxy) {
    this.useDefaultProxySelector = useDefaultProxySelector;
    this.setupSystemProperties = setupSystemProperties;
    this.useEnvironmentVariables = useEnvironmentVariables;
    this.proxies = Objects.requireNonNullElseGet(proxies, List::of);
    this.proxy = Objects.requireNonNullElseGet(proxy, ProxySetting::new);
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

  public ProxySetting getProxy() {
    return proxy;
  }

  public static class ProxySetting {
    private final Set<Scheme> schemes;
    private final URI uri;
    private final List<String> includes;
    private final List<String> excludes;
    private final BasicAuthenticationSetting basicAuthentication;

    public ProxySetting() {
      this(Set.of(), null, null, List.of(), List.of(), new BasicAuthenticationSetting());
    }

    @ConstructorBinding
    public ProxySetting(
        Set<Scheme> schemes,
        Scheme scheme,
        URI uri,
        List<String> includes,
        List<String> excludes,
        BasicAuthenticationSetting basicAuthentication) {
      this.schemes = Objects.requireNonNullElseGet(schemes, Set::of);
      if (scheme != null) {
        schemes.add(scheme);
      }
      this.uri = uri;
      this.includes = Objects.requireNonNullElseGet(includes, List::of);
      this.excludes = Objects.requireNonNullElseGet(excludes, List::of);
      this.basicAuthentication =
          Objects.requireNonNullElseGet(basicAuthentication, BasicAuthenticationSetting::new);
    }

    public Set<Scheme> getSchemes() {
      return schemes;
    }

    public URI getUri() {
      return uri;
    }

    public List<String> getIncludes() {
      return includes;
    }

    public List<String> getExcludes() {
      return excludes;
    }

    public BasicAuthenticationSetting getBasicAuthentication() {
      return basicAuthentication;
    }

    public static class BasicAuthenticationSetting {
      private final String username;
      private final String password;

      public BasicAuthenticationSetting() {
        this(null, null);
      }

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
