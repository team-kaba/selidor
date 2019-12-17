package pw.itr0.selidor.http.proxy.matcher;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;
import pw.itr0.selidor.http.Scheme;

public class SchemeMatcher {

  private final Set<String> schemes;

  public SchemeMatcher(Set<Scheme> schemes) {
    this.schemes =
        schemes.stream()
            .map(Scheme::getUriScheme)
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
  }

  public boolean matches(URI uri) {
    final String provided = uri.getScheme();
    return provided != null && schemes.contains(provided.toLowerCase());
  }
}
