package pw.itr0.selidor.type;

import java.util.UUID;

/**
 * 型付けられたID({@link UUID})を表すクラスを実装するための抽象クラスです。
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
 * public final class EventId extends TypedUuid&lt;EventId&gt; {
 *   public EventId(UUID value) {
 *     super(value, true);
 *   }
 * }
 * </code></pre>
 *
 * @param <SELF> このクラスを実装するクラス自身
 */
public abstract class TypedUuid<SELF extends TypedUuid<? super SELF>>
    extends TypedComparable<SELF, UUID> {

  /**
   * @param value 値
   * @param nullFirst ソート時に {@code null} を先頭にするか末尾にするか。 {@code true} の場合、 {@code null} を先頭としてソートする。
   */
  protected TypedUuid(UUID value, boolean nullFirst) throws IllegalArgumentException {
    super(value, nullFirst);
  }

  /**
   * {@link UUID#getLeastSignificantBits()}の値を返します。値が{@code null}の場合は、{@link
   * NullValueUnboxingException}が送出されます。
   *
   * @return {@link UUID#getLeastSignificantBits()}
   */
  public long getLeastSignificantBits() throws NullValueUnboxingException {
    return getValue()
        .map(UUID::getLeastSignificantBits)
        .orElseThrow(() -> new NullValueUnboxingException(getClass()));
  }

  /**
   * {@link UUID#getMostSignificantBits()}の値を返します。値が{@code null}の場合は、{@link
   * NullValueUnboxingException}が送出されます。
   *
   * @return {@link UUID#getMostSignificantBits()}
   */
  public long getMostSignificantBits() throws NullValueUnboxingException {
    return getValue()
        .map(UUID::getMostSignificantBits)
        .orElseThrow(() -> new NullValueUnboxingException(getClass()));
  }

  /**
   * {@link UUID#version()}の値を返します。値が{@code null}の場合は、{@link
   * NullValueUnboxingException}が送出されます。
   *
   * @return {@link UUID#version()}
   */
  public int version() throws NullValueUnboxingException {
    return getValue()
        .map(UUID::version)
        .orElseThrow(() -> new NullValueUnboxingException(getClass()));
  }

  /**
   * {@link UUID#variant()}の値を返します。値が{@code null}の場合は、{@link NullValueUnboxingException}が送出されます。
   *
   * @return {@link UUID#variant()}
   */
  public int variant() throws NullValueUnboxingException {
    return getValue()
        .map(UUID::variant)
        .orElseThrow(() -> new NullValueUnboxingException(getClass()));
  }
}
