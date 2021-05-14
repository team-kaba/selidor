package pw.itr0.selidor.type;

import java.util.Comparator;

/**
 * 型付けられた値が比較可能であるクラスを実装するための抽象クラスです。
 *
 * <p>{@link #getValue()} で取得した値の実装する {@link Comparable#compareTo} で比較が行われます。
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

  private final Comparator<? super RAW> comparator;

  /**
   * @param value 値
   * @param nullFirst ソート時に {@code null} を先頭にするか末尾にするか。 {@code true} の場合、 {@code null} を先頭としてソートする。
   */
  protected TypedComparable(RAW value, boolean nullFirst) throws IllegalArgumentException {
    this(
        value,
        nullFirst
            ? Comparator.nullsFirst(Comparator.naturalOrder())
            : Comparator.nullsLast(Comparator.naturalOrder()));
  }

  /**
   * @param value 値
   * @param comparator ソート時に利用する {@link Comparator}
   */
  protected TypedComparable(RAW value, Comparator<? super RAW> comparator)
      throws IllegalArgumentException {
    super(value);
    this.comparator = comparator;
  }

  /**
   * 値自体の {@code compareTo} の結果を返します。
   *
   * <p>比較対象の値として {@code null} が渡された場合、 {@link NullPointerException} を送出します。
   *
   * @param other 比較対象のオブジェクト
   * @return このオブジェクトが比較対象と比較して小さい時に {@code -1}, 同値の時に {@code 0}, 大きい時に {@code 1}
   */
  @Override
  public int compareTo(SELF other) {
    return comparator.compare(this.getNullableValue(), other.getNullableValue());
  }
}
