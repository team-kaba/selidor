package pw.itr0.selidor.type;

import pw.itr0.selidor.util.PreConditions;

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
  private final Class<RAW> rawValueClass;

  /**
   * @param value 値
   * @throws IllegalArgumentException {@code value} が {@code null} の場合
   */
  @SuppressWarnings("unchecked")
  protected TypedValue(RAW value) {
    PreConditions.requireNonNull(
        value, () -> "`value` must not be null. Instead, make variable itself be null.");
    this.value = value;
    this.rawValueClass = (Class<RAW>) value.getClass();
  }

  /** @return 元の値 */
  public RAW getValue() {
    return this.value;
  }

  /** @return 元の値の型 */
  public Class<RAW> getRawValueClass() {
    return this.rawValueClass;
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

    return getValue().equals(that.getValue());
  }

  @Override
  public int hashCode() {
    return getValue().hashCode();
  }
}
