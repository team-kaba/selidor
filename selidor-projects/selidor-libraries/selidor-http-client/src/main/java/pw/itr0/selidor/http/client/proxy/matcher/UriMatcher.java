package pw.itr0.selidor.http.client.proxy.matcher;

import java.net.URI;
import java.util.List;
import java.util.Set;
import pw.itr0.selidor.http.client.Scheme;

public class UriMatcher {

  private final SchemeMatcher schemeMatcher;
  private final HostNameMatcher hostNameMatcher;

  public UriMatcher(Set<Scheme> schemes, List<String> includes, List<String> excludes) {
    schemeMatcher = new SchemeMatcher(schemes);
    hostNameMatcher =
        includes.isEmpty() && excludes.isEmpty()
            ? HostNameMatcher.MATCH_ANY
            : new HostNameMatcher(includes, excludes);
  }

  public boolean matches(URI uri) {
    return schemeMatcher.matches(uri) && hostNameMatcher.matches(uri);
  }

  public String getEquivalentNoProxyHosts() {
    return null;
  }

  public String getEquivalentNoProxyHostsAsRegex() {
    return null;
  }
}
