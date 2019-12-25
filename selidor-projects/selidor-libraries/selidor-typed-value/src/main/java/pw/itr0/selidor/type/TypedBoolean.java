package pw.itr0.selidor.type;

/**
 * 型付けられた真偽値を表すクラスを実装するための抽象クラスです。
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
 * public final class Enabled extends TypedBoolean&lt;Enabled&gt; {
 *   public Enabled(boolean value) {
 *     super(value, false);
 *   }
 * }
 * </code></pre>
 *
 * @param <SELF> このクラスを実装するクラス自身
 */
public abstract class TypedBoolean<SELF extends TypedBoolean<? super SELF>>
    extends TypedComparable<SELF, Boolean> {

  /**
   * @param value 状態に対応する真偽値
   * @param nullFirst ソート時に {@code null} を先頭にするか末尾にするか。 {@code true} の場合、 {@code null} を先頭としてソートする。
   */
  protected TypedBoolean(Boolean value, boolean nullFirst) {
    super(value, nullFirst);
  }

  /**
   * 元の値が {@code true} である時、 {@code true} を返します。 {@code null} の場合は {@code false} となります。
   *
   * @return クラスの表す状態が「真」をあらわす時、 {@code true}
   */
  public boolean isTrue() {
    return getValue().orElse(false);
  }

  /**
   * 元の値が {@code false} または {@code null} である時、 {@code true} を返します。
   *
   * @return クラスの表す状態が「真」をあらわす時、 {@code false}
   */
  public boolean isFalse() {
    return !isTrue();
  }
}
