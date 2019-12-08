package pw.itr0.selidor.type;

import java.time.Instant;
import java.util.UUID;
import pw.itr0.selidor.identifier.crid.Crid;

/**
 * 型付けられたID({@link Crid})を表すクラスを実装するための抽象クラスです。
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
 * public final class EventId extends TypedCrid&lt;EventId&gt; {
 *   public EventId(Crid value) {
 *     super(value);
 *   }
 * }
 * </code></pre>
 *
 * @param <SELF> このクラスを実装するクラス自身
 */
public abstract class TypedCrid<SELF extends TypedCrid<? super SELF>>
    extends TypedComparable<SELF, Crid> {

  /**
   * @param value 値
   * @throws IllegalArgumentException {@code value} が {@code null} の場合
   */
  protected TypedCrid(Crid value) {
    super(value);
  }

  /**
   * CRIDのタイムスタンプ部分を返します。
   *
   * @return CRIDのタイムスタンプ
   */
  public Instant timestamp() {
    return getValue().timestamp();
  }

  /**
   * CRIDの乱数部分を返します。
   *
   * @return CRIDの乱数部分
   */
  public byte[] randomness() {
    return getValue().randomness();
  }

  /**
   * CRIDを128bitのビット列として見た時のUUID表現を返します。
   *
   * @return CRIDのUUID表現
   */
  public UUID uuid() {
    return getValue().uuid();
  }
}
