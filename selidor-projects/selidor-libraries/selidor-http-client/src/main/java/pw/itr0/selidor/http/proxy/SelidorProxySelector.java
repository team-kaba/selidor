package pw.itr0.selidor.http.proxy;

import java.io.IOException;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelidorProxySelector extends ProxySelector {

  private final Logger log = LoggerFactory.getLogger(getClass());
  private final HttpProxies proxies;

  public SelidorProxySelector(HttpProxies delegate) {
    this.proxies = delegate;
  }

  @Override
  public List<Proxy> select(URI uri) {
    return proxies.select(uri).stream().map(HttpProxy::getProxy).collect(Collectors.toList());
  }

  @Override
  public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
    if (uri == null || sa == null || ioe == null) {
      throw new IllegalArgumentException("Arguments can't be null.");
    }
    log.debug("Failed to connect URI through a proxy. uri=[{}], proxy=[{}]", uri, sa, ioe);
  }
}
