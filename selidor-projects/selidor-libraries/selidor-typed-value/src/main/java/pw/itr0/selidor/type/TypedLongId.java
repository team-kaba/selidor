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
 *     super(value, true);
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
   * @param nullFirst ソート時に {@code null} を先頭にするか末尾にするか。 {@code true} の場合、 {@code null} を先頭としてソートする。
   */
  protected TypedLongId(LongId value, boolean nullFirst) {
    super(value, nullFirst);
  }

  /**
   * IDの値を {@code long} として返します。元の値の {@link LongId} が {@code null} の場合、 {@link
   * IllegalStateException} を送出します。
   *
   * @return IDの値
   * @throws IllegalStateException 元の値が {@code null} の場合
   */
  public long longValue() {
    return getValue()
        .map(LongId::longValue)
        .orElseThrow(
            () -> new NullValueException(getClass().getSimpleName() + " is holding null."));
  }
}
