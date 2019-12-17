package pw.itr0.selidor.http.proxy;

import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import pw.itr0.selidor.http.AuthMethod;
import pw.itr0.selidor.http.Scheme;

public class HttpProxies {

  private static final ProxySelector DEFAULT_SELECTOR = ProxySelector.getDefault();

  private final List<HttpProxy> proxies;
  private final boolean useDefaultProxySelector;
  private final SelidorProxySelector proxySelector;
  private final ConcurrentMap<URI, List<HttpProxy>> cache = new ConcurrentHashMap<>();

  public HttpProxies(
      List<HttpProxy> proxies, boolean useDefaultProxySelector, boolean setupSystemProperties) {
    this.proxies = proxies;
    this.useDefaultProxySelector = useDefaultProxySelector;
    this.proxySelector = new SelidorProxySelector(this);
    if (setupSystemProperties) {
      setupSystemProperties();
    }
    allowBasicAuthTunnelingIfPresent();
  }

  public Optional<HttpProxy> primary() {
    return proxies.stream().findFirst();
  }

  public List<HttpProxy> select(URI uri) {
    return cache.computeIfAbsent(
        uri,
        u -> {
          final List<HttpProxy> customProxies =
              proxies.stream().filter(p -> p.matches(uri)).collect(Collectors.toList());
          if (useDefaultProxySelector) {
            final List<Proxy> proxy = DEFAULT_SELECTOR.select(uri);
            customProxies.addAll(
                proxy.stream()
                    .map(HttpProxy::from)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));
          }
          return customProxies;
        });
  }

  public Optional<HttpProxy> selectOne(URI uri) {
    return select(uri).stream().findFirst();
  }

  public ProxySelector getProxySelector() {
    return proxySelector;
  }

  private void setupSystemProperties() {
    final List<HttpProxy> ordered = new ArrayList<>(proxies);
    Collections.reverse(ordered);
    for (HttpProxy proxy : ordered) {
      for (Scheme scheme : proxy.getSchemes()) {
        System.setProperty(scheme.getUriScheme() + ".proxyHost", proxy.getHost());
        System.setProperty(scheme.getUriScheme() + ".proxyPort", Integer.toString(proxy.getPort()));
        System.setProperty(scheme.getUriScheme() + ".nonProxyHosts", proxy.getNonProxyHosts());
      }
    }
  }

  private void allowBasicAuthTunnelingIfPresent() {
    proxies.stream()
        .filter(
            proxy ->
                proxy.getSchemes().contains(Scheme.HTTPS)
                    && AuthMethod.BASIC.equals(proxy.getAuthMethod()))
        .findAny()
        .ifPresent(proxy -> System.setProperty("jdk.http.auth.tunneling.disabledSchemes", ""));
  }
}
