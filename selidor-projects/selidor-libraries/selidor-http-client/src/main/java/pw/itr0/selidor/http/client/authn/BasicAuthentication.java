package pw.itr0.selidor.http.client.authn;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import pw.itr0.selidor.http.client.AuthMethod;

/** Basic認証をあらわす {@link HttpAuthentication} です。 */
public class BasicAuthentication implements HttpAuthentication<UserPasswordCredential> {

  /** 認証に利用するクレデンシャル */
  private final UserPasswordCredential credential;

  /**
   * ユーザ名とパスワードを指定して、 {@link BasicAuthentication} を生成します。
   *
   * @param username Basic認証に利用するユーザ名
   * @param password Basic認証に利用するパスワード
   */
  public BasicAuthentication(String username, String password) {
    this.credential = new UserPasswordCredential(username, password);
  }

  /** @return {@link AuthMethod#BASIC} */
  @Override
  public AuthMethod getMethod() {
    return AuthMethod.BASIC;
  }

  @Override
  public Map<String, List<String>> getProxyAuthorizationHeaders() {
    final String encoded =
        Base64.getEncoder()
            .encodeToString(
                (credential.getUsername() + ":" + credential.getPassword())
                    .getBytes(StandardCharsets.UTF_8));
    return Map.of(PROXY_AUTHORIZATION_HEADER, List.of("Basic " + encoded));
  }
}
