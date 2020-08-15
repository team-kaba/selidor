package pw.itr0.selidor.type;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

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
 * <h3>{@code null}の扱いに関する規約</h3>
 *
 * <p>値として{@code null}を保持している{@code TypedValue}に対して、値に対してアクセスしてオブジェクトを返すメソッドが呼び出された場合は、{@code
 * null}を返してください。
 *
 * <p>プリミティブ型を返すメソッドが呼び出された場合、{@link NullValueUnboxingException}を送出してください。
 *
 * @param <RAW> 値の型
 */
public abstract class TypedValue<RAW> {

  private final RAW raw;

  /** @param raw 値 */
  protected TypedValue(@Nullable RAW raw) {
    this.raw = raw;
  }

  /** @return 元の値 */
  @Nonnull
  public Optional<RAW> getValue() {
    return Optional.ofNullable(this.raw);
  }

  /** @return 元の値 */
  @Nullable
  public RAW getNullableValue() {
    return this.raw;
  }

  /** @return 元の値が {@code null} の場合 {@code true} */
  public boolean isNull() {
    return this.raw == null;
  }

  /** @return 元の値が {@code null} でない場合 {@code true} */
  public boolean isNotNull() {
    return !isNull();
  }

  @Override
  public String toString() {
    return String.valueOf(raw);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TypedValue<?> that = (TypedValue<?>) o;

    return Objects.equals(raw, that.raw);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(raw);
  }
}
