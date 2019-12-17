package pw.itr0.selidor.http.proxy.matcher;

import java.net.URI;
import java.util.List;

public class HostNameMatcher {

  public static final HostNameMatcher MATCH_ANY =
      new HostNameMatcher() {
        @Override
        public boolean matches(URI url) {
          return true;
        }
      };

  private final List<String> includes;
  private final List<String> excludes;

  private HostNameMatcher() {
    this(List.of(), List.of());
  }

  public HostNameMatcher(List<String> includes, List<String> excludes) {
    this.includes = includes;
    this.excludes = excludes;
  }

  public boolean matches(URI url) {
    final String host = url.getHost();
    return includes.contains("*") || (includes.contains(host) && !excludes.contains(host));
  }
}
