package pw.itr0.selidor.http;

import java.util.EnumSet;
import java.util.Set;

public enum Scheme {
  HTTP("http", 80),
  HTTPS("https", 443);

  public static Scheme of(String scheme) {
    return Scheme.valueOf(scheme.toUpperCase());
  }

  public static final Set<Scheme> ANY = EnumSet.allOf(Scheme.class);
  private final String scheme;
  private final int port;

  Scheme(String scheme, int port) {
    this.scheme = scheme;
    this.port = port;
  }

  public int getReservedPort() {
    return port;
  }

  public String getUriScheme() {
    return scheme;
  }
}
