package pw.itr0.selidor.http.client.proxy;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pw.itr0.selidor.http.client.Scheme;
import pw.itr0.selidor.internal.util.StringUtils;

public abstract class EnvVarProxyLoader {

  private static final Logger log = LoggerFactory.getLogger(EnvVarProxyLoader.class);

  public static Optional<HttpProxy> load(String var, Scheme scheme) {
    final String value = System.getenv(var);
    if (StringUtils.isNotBlank(value)) {
      try {
        return Optional.of(new HttpProxy(Set.of(scheme), new URI(value)));
      } catch (URISyntaxException e) {
        log.info("Failed to parse proxy url: {}=[{}]", var, value, e);
      }
    }
    return Optional.empty();
  }
}
