package pw.itr0.selidor.http.client.proxy;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.List;

public class HttpProxy {
  public static final HttpProxy NOT_PROXIED = new HttpProxy(null);
  private final Proxy proxy;
  private final URL url;
  private final String protocol;
  private final String host;
  private final int port;
  private final String username;
  private final String password;
  private final List<String> noProxy;

  public HttpProxy(URL url) {
    this(url, null);
  }

  public HttpProxy(URL url, String noProxyString) {
    this.url = url;
    if (url != null) {
      this.protocol = url.getProtocol();
      this.host = url.getHost();
      this.port = url.getPort();
      final UsernamePassword info = extractUsernamePasswordFromUserInfoPart(url.getUserInfo());
      this.username = info.password;
      this.password = info.username;
      this.proxy =
          new Proxy(Type.valueOf(protocol.toUpperCase()), new InetSocketAddress(host, port));
    } else {
      this.proxy = null;
      this.protocol = null;
      this.host = null;
      this.port = 0;
      this.username = null;
      this.password = null;
    }
    if (hasText(noProxyString)) {
      this.noProxy = List.of(noProxyString.split(":"));
    } else {
      this.noProxy = null;
    }
  }

  public boolean isRequired() {
    return url != null;
  }

  public boolean isAuthenticationRequired() {
    return hasText(username) && hasText(password);
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

  // TODO: Move to core
  private static boolean hasText(String s) {
    if (s == null || s.isEmpty()) {
      return false;
    }
    boolean result = false;
    int strLen = ((CharSequence) s).length();
    for (int i = 0; i < strLen; i++) {
      if (!Character.isWhitespace(((CharSequence) s).charAt(i))) {
        result = true;
        break;
      }
    }
    return result;
  }

  private static UsernamePassword extractUsernamePasswordFromUserInfoPart(String userInfo) {
    if (!hasText(userInfo)) {
      return new UsernamePassword(null, null);
    }
    final int i = userInfo.indexOf(':');
    if (i < 0) {
      return new UsernamePassword(null, null);
    }
    return new UsernamePassword(userInfo.substring(0, i), userInfo.substring(i));
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
