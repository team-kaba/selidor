package pw.itr0.selidor.type;

/**
 * 型付けられた文字列を表すクラスを実装するための抽象クラスです。
 *
 * <h3>継承に関する規約</h3>
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
 *     super(value);
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
   * @throws IllegalArgumentException {@code value} が {@code null} の場合
   */
  protected TypedString(String value) {
    super(value);
  }

  /** @return 値が空文字列の場合 {@code true} */
  public boolean isEmpty() {
    return getValue().isEmpty();
  }

  /** @return 値が空文字列でない場合 {@code true} */
  public boolean hasLength() {
    return !isEmpty();
  }
}
