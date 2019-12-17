package pw.itr0.selidor.http.authn;

import org.jetbrains.annotations.NotNull;
import pw.itr0.selidor.internal.util.PreConditions;

/** ユーザ名とパスワードで認証する場合の資格情報を保持するクラスです。 */
class UserPasswordCredential implements Credential {

  /** ユーザ名 */
  private final String username;

  /** パスワード */
  private final String password;

  /**
   * 認証に利用するユーザ名とパスワードを指定して資格情報を生成します。
   *
   * @param username 認証に利用するユーザ名
   * @param password 認証に利用するパスワード
   */
  public UserPasswordCredential(@NotNull String username, @NotNull String password) {
    PreConditions.requireNonEmpty(username, () -> "username must not be null or empty.");
    PreConditions.requireNonEmpty(password, () -> "password must not be null or empty.");
    this.username = username;
    this.password = password;
  }

  String getUsername() {
    return username;
  }

  String getPassword() {
    return password;
  }
}
