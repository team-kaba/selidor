package pw.itr0.selidor.network.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.List;
import pw.itr0.selidor.internal.util.StringUtils;

public class NetworkProxy {
  public static final NetworkProxy NOT_PROXIED = new NetworkProxy(null);
  private final Proxy proxy;
  private final URL url;
  private final String protocol;
  private final String host;
  private final int port;
  private final String username;
  private final String password;
  private final List<String> noProxy;

  public NetworkProxy(URL url) {
    this(url, null);
  }

  public NetworkProxy(URL url, String noProxyString) {
    this.url = url;
    if (url != null) {
      this.protocol = url.getProtocol();
      this.host = url.getHost();
      this.port = url.getPort();
      final UsernamePassword info = extractUsernamePasswordFromUserInfoPart(url.getUserInfo());
      this.username = info.username;
      this.password = info.password;
      this.proxy =
          new Proxy(Type.valueOf(protocol.toUpperCase()), new InetSocketAddress(host, port));
    } else {
      this.proxy = Proxy.NO_PROXY;
      this.protocol = null;
      this.host = null;
      this.port = 0;
      this.username = null;
      this.password = null;
    }
    if (StringUtils.hasText(noProxyString)) {
      this.noProxy = List.of(noProxyString.split(":"));
    } else {
      this.noProxy = null;
    }
  }

  public boolean isRequired() {
    return url != null;
  }

  public boolean isAuthenticationRequired() {
    return StringUtils.hasText(username) && StringUtils.hasText(password);
  }

  public Proxy getProxy() {
    return proxy;
  }

  public URL getUrl() {
    return url;
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

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public List<String> getNoProxy() {
    return noProxy;
  }

  private static UsernamePassword extractUsernamePasswordFromUserInfoPart(String userInfo) {
    if (!StringUtils.hasText(userInfo)) {
      return new UsernamePassword(null, null);
    }
    final int i = userInfo.indexOf(':');
    if (i < 0) {
      return new UsernamePassword(null, null);
    }
    return new UsernamePassword(userInfo.substring(0, i), userInfo.substring(i + 1));
  }

  private static final class UsernamePassword {
    private final String username;
    private final String password;

    private UsernamePassword(String username, String password) {
      this.username = username;
      this.password = password;
    }
  }
}
