package pw.itr0.selidor.http.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jetbrains.annotations.NotNull;
import pw.itr0.selidor.http.AuthMethod;
import pw.itr0.selidor.http.Scheme;
import pw.itr0.selidor.http.authn.BasicAuthentication;
import pw.itr0.selidor.http.authn.HttpAuthentication;
import pw.itr0.selidor.http.proxy.matcher.UriMatcher;

/**
 * HTTP Proxyをあらわすクラスです。
 *
 * <p>{@link Proxy} とは異なり、プロキシの種類({@link Type}) としては {@link Type#HTTP} しか指定することはできません。また、{@link
 * Proxy} が持つプロキシのURIに関する情報以外に次の情報を持ちます。
 *
 * <ul>
 *   <li>どのプロトコルに適用するべきか({@link Scheme schemes})
 *   <li>どのホストに適用するべきか({@code includes})
 *   <li>どのホストに適用してはいけないか({@code excludes})
 *   <li>プロキシに対して必要な認証方法({@link HttpAuthentication authentication})
 * </ul>
 *
 * <p>{@link #matches(URI)} が {@code true} を返す場合は、このプロキシが適用されるべきURIであり、 {@code false}
 * を返す場合は適用してはいけません。 {@link #matches(URI)} は次のように判定されます。
 *
 * <ol>
 *   <li>{@code includes}
 * </ol>
 */
public class HttpProxy {

  private static final List<String> DEFAULT_INCLUDED_HOST = List.of("*");
  private static final List<String> DEFAULT_EXCLUDED_HOST =
      List.of("localhost", "127.*", "[::1]", "0.0.0.0", "[::0]");
  private static final ConcurrentMap<Proxy, HttpProxy> cache = new ConcurrentHashMap<>();

  private final URI uri;
  private final HttpAuthentication<?> authentication;
  private final Set<Scheme> schemes;
  private final UriMatcher matcher;

  // derived fields
  private final Proxy proxy;
  private final String protocol;
  private final String host;
  private final int port;

  public static HttpProxy from(Proxy proxy) {
    return cache.computeIfAbsent(
        proxy,
        p -> {
          if (!Type.HTTP.equals(p.type()) || p.address() == null) {
            return null;
          }
          try {
            return new HttpProxy(
                Scheme.ANY, new URI(p.type().toString().toLowerCase() + "://" + p.address()));
          } catch (URISyntaxException e) {
            throw new IllegalArgumentException("", e);
          }
        });
  }

  public HttpProxy(Set<Scheme> schemes, URI uri) {
    this(schemes, uri, DEFAULT_INCLUDED_HOST, DEFAULT_EXCLUDED_HOST);
  }

  public HttpProxy(Set<Scheme> schemes, URI uri, List<String> includes, List<String> excludes) {
    this(schemes, uri, includes, excludes, null);
  }

  private HttpProxy(
      Set<Scheme> schemes,
      URI uri,
      List<String> includes,
      List<String> excludes,
      HttpAuthentication<?> authentication) {
    this.schemes = schemes;
    this.uri = uri;
    this.matcher = new UriMatcher(schemes, includes, excludes);
    if (uri != null) {
      this.protocol = uri.getScheme();
      this.host = uri.getHost();
      this.port = 0 < uri.getPort() ? uri.getPort() : Scheme.of(this.protocol).getReservedPort();
      this.proxy =
          new Proxy(Type.valueOf(protocol.toUpperCase()), new InetSocketAddress(host, this.port));
    } else {
      this.proxy = Proxy.NO_PROXY;
      this.protocol = null;
      this.host = null;
      this.port = 0;
    }
    this.authentication = authentication;
  }

  public boolean matches(URI uri) {
    return matcher.matches(uri);
  }

  public Set<Scheme> getSchemes() {
    return schemes;
  }

  public URI getUri() {
    return uri;
  }

  public boolean isAuthenticationRequired() {
    return authentication != null;
  }

  public Proxy getProxy() {
    return proxy;
  }

  public String getProtocol() {
    return protocol;
  }

  public String getHost() {
    return host;
  }

  public int getPort() {
    return port;
  }

  public String getNonProxyHosts() {
    return matcher.getEquivalentNoProxyHosts();
  }

  public String getNonProxyHostsRegex() {
    return matcher.getEquivalentNoProxyHostsAsRegex();
  }

  public AuthMethod getAuthMethod() {
    return authentication != null ? authentication.getMethod() : null;
  }

  public Map<String, List<String>> getAuthorizationHeaders() {
    return authentication != null ? authentication.getProxyAuthorizationHeaders() : Map.of();
  }

  public static class Builder {
    private final Set<Scheme> schemes = new HashSet<>(2);
    private URI uri;
    private final List<String> includes = new ArrayList<>();
    private final List<String> excludes = new ArrayList<>();
    private HttpAuthentication<?> authentication;

    public Builder addSchemes(Set<Scheme> schemes) {
      this.schemes.addAll(schemes);
      return this;
    }

    public Builder addScheme(Scheme scheme) {
      this.schemes.add(scheme);
      return this;
    }

    public Builder uri(URI uri) {
      this.uri = uri;
      return this;
    }

    public Builder addIncludes(@NotNull List<String> includes) {
      this.includes.addAll(includes);
      return this;
    }

    public Builder addExcludes(@NotNull List<String> excludes) {
      this.excludes.addAll(excludes);
      return this;
    }

    public Builder basicAuthentication(@NotNull String username, @NotNull String password) {
      this.authentication = new BasicAuthentication(username, password);
      return this;
    }

    public HttpProxy build() {
      return new HttpProxy(
          this.schemes, this.uri, this.includes, this.excludes, this.authentication);
    }
  }
}
