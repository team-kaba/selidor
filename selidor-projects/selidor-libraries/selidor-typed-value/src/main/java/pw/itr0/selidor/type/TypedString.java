package pw.itr0.selidor.type;

import pw.itr0.selidor.internal.util.StringUtils;

/**
 * 型付けられた文字列を表すクラスを実装するための抽象クラスです。
 *
 * <h2>継承に関する規約</h2>
 *
 * <p>この抽象クラスを継承するクラスは、以下の規約に従ってください。
 *
 * <ul>
 *   <li>{@code abstract} または {@code final} なクラスとして実装してください。
 *   <li>型パラメータ {@code <SELF>} に自分自身の型を設定してください。
 * </ul>
 *
 * <p>実装クラスの例は次のようになります。
 *
 * <pre><code class="java">
 * public final class Title extends TypedString&lt;Title&gt; {
 *   public Title(String value) {
 *     super(value, false);
 *   }
 * }
 * </code></pre>
 *
 * @param <SELF> このクラスを実装するクラス自身
 */
public abstract class TypedString<SELF extends TypedString<? super SELF>>
    extends TypedComparable<SELF, String> {

  /**
   * @param value 値
   * @param nullFirst ソート時に {@code null} を先頭にするか末尾にするか。 {@code true} の場合、 {@code null} を先頭としてソートする。
   */
  protected TypedString(String value, boolean nullFirst) {
    super(value, nullFirst);
  }

  /** @return 値が空文字列の場合 {@code true} */
  public boolean isEmpty() {
    return StringUtils.isEmpty(getNullableValue());
  }

  /** @return 値が空文字列でない場合 {@code true} */
  public boolean isNotEmpty() {
    return !isEmpty();
  }

  /** @return 値が空白文字のみから構成される文字列の場合 {@code true} */
  public boolean isBlank() {
    return StringUtils.isBlank(getNullableValue());
  }

  /** @return 値が空白文字のみから構成される文字列でない場合 {@code true} */
  public boolean isNotBlank() {
    return !isBlank();
  }
}
