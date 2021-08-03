package pw.itr0.selidor.type;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import pw.itr0.selidor.identifier.crid.Crid;

/**
 * 型付けられたID({@link Crid})を表すクラスを実装するための抽象クラスです。
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
 * public final class EventId extends TypedCrid&lt;EventId&gt; {
 *   public EventId(Crid value) {
 *     super(value, true);
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
   * @param nullFirst ソート時に {@code null} を先頭にするか末尾にするか。 {@code true} の場合、 {@code null} を先頭としてソートする。
   */
  protected TypedCrid(Crid value, boolean nullFirst) {
    super(value, nullFirst);
  }

  /**
   * CRIDのタイムスタンプ部分を返します。
   *
   * @return CRIDのタイムスタンプ
   */
  public Optional<Instant> timestamp() {
    return getValue().map(Crid::timestamp);
  }

  /**
   * CRIDの乱数部分を返します。
   *
   * @return CRIDの乱数部分
   */
  public Optional<byte[]> randomness() {
    return getValue().map(Crid::randomness);
  }

  /**
   * CRIDを128bitのビット列として見た時のUUID表現を返します。
   *
   * @return CRIDのUUID表現
   */
  public Optional<UUID> uuid() {
    return getValue().map(Crid::uuid);
  }
}
