package pw.itr0.selidor.http.client.okhttp3;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import org.jetbrains.annotations.NotNull;
import pw.itr0.selidor.http.proxy.HttpProxies;
import pw.itr0.selidor.http.proxy.HttpProxy;

public class OkHttp3PreemptiveProxyAuthenticator implements Authenticator {

  private final HttpProxies proxies;

  public OkHttp3PreemptiveProxyAuthenticator(HttpProxies proxies) {
    this.proxies = proxies;
  }

  @Override
  public Request authenticate(Route route, @NotNull Response response) {
    if (isPreemptiveAuthRequired(response)) {
      final Request request = response.request();
      final Optional<HttpProxy> proxy = proxies.selectOne(request.url().uri());
      final Request.Builder builder = request.newBuilder();
      proxy
          .filter(HttpProxy::isAuthenticationRequired)
          .ifPresent(p -> addProxyAuthorizationHeaders(p, builder));
      return builder.build();
    }
    return null; // Didn't find a preemptive auth scheme.
  }

  private boolean isPreemptiveAuthRequired(Response response) {
    return response.challenges().stream()
        // If there is preemptive auth, use a preemptive credential.
        .anyMatch(c -> c.scheme().equalsIgnoreCase("OkHttp-Preemptive"));
  }

  private void addProxyAuthorizationHeaders(HttpProxy proxy, Request.Builder builder) {
    final Map<String, List<String>> headers = proxy.getAuthorizationHeaders();
    headers.forEach((key, values) -> values.forEach(value -> builder.addHeader(key, value)));
  }
}
