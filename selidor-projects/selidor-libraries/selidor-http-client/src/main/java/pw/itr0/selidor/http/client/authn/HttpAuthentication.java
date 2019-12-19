package pw.itr0.selidor.http.client.authn;

import java.util.List;
import java.util.Map;
import pw.itr0.selidor.http.client.AuthMethod;

public interface HttpAuthentication<T extends Credential> {
  String PROXY_AUTHORIZATION_HEADER = "Proxy-Authorization";

  AuthMethod getMethod();

  /**
   * プロキシで認証するために必要なHTTPヘッダを返します。
   *
   * @return プロキシで認証するために必要なHTTPヘッダ
   */
  Map<String, List<String>> getProxyAuthorizationHeaders();
}
