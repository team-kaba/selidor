package pw.itr0.selidor.type;

/**
 * 型付けられた値をあらわす抽象クラスです。
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
 * @param <RAW> 値の型
 */
public abstract class TypedValue<RAW> {
  private final RAW value;

  /** @param value 値 */
  protected TypedValue(RAW value) {
    this.value = value;
  }

  /** @return 元の値 */
  public RAW getValue() {
    return this.value;
  }

  /** @return 元の値が {@code null} の場合 {@code true} */
  public boolean isNull() {
    return this.value == null;
  }

  /** @return 元の値が {@code null} でない場合 {@code true} */
  public boolean isNotNull() {
    return !isNull();
  }

  @Override
  public String toString() {
    return String.valueOf(getValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TypedValue<?> that = (TypedValue<?>) o;

    return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
  }

  @Override
  public int hashCode() {
    return getValue() != null ? getValue().hashCode() : 0;
  }
}
