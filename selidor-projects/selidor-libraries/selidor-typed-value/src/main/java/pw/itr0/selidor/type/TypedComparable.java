package pw.itr0.selidor.type;

/**
 * 型付けられた値が比較可能であるクラスを実装するための抽象クラスです。
 *
 * <p>{@link #getValue()} で取得した値の {@link Comparable#compareTo} で比較が行われます。
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
 * @param <SELF> このクラスを実装するクラス自身
 * @param <RAW> 値の型
 */
public abstract class TypedComparable<
        SELF extends TypedValue<RAW>, RAW extends Comparable<? super RAW>>
    extends TypedValue<RAW> implements Comparable<SELF> {

  /**
   * @param value 値
   * @throws IllegalArgumentException {@code value} が {@code null} の場合
   */
  protected TypedComparable(RAW value) throws IllegalArgumentException {
    super(value);
  }

  @Override
  public int compareTo(SELF other) {
    return this.getValue().compareTo(other.getValue());
  }
}
