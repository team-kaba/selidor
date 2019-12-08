package pw.itr0.selidor.type;

import pw.itr0.selidor.identifier.random.LongId;

/**
 * 型付けられたID({@link LongId})を表すクラスを実装するための抽象クラスです。
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
 * public final class EventId extends TypedLongId&lt;EventId&gt; {
 *   public EventId(LongId value) {
 *     super(value);
 *   }
 * }
 * </code></pre>
 *
 * @param <SELF> このクラスを実装するクラス自身
 */
public abstract class TypedLongId<SELF extends TypedLongId<? super SELF>>
    extends TypedComparable<SELF, LongId> {

  /**
   * @param value 値
   * @throws IllegalArgumentException {@code value} が {@code null} の場合
   */
  protected TypedLongId(LongId value) {
    super(value);
  }

  /**
   * IDの値を {@code long} として返します。
   *
   * @return IDの値
   */
  public long longValue() {
    return getValue().longValue();
  }
}
