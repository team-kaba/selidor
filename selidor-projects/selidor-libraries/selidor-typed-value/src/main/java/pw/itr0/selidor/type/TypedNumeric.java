package pw.itr0.selidor.type;

import java.util.function.Function;

public abstract class TypedNumeric<
        SELF extends TypedNumeric<? super SELF, RAW>, RAW extends Number & Comparable<RAW>>
    extends TypedComparable<SELF, RAW> {

  protected TypedNumeric(RAW value, boolean nullFirst) {
    super(value, nullFirst);
  }

  public int intValue() {
    return getPrimitiveValue(Number::intValue);
  }

  public long longValue() {
    return getPrimitiveValue(Number::longValue);
  }

  public float floatValue() {
    return getPrimitiveValue(Number::floatValue);
  }

  public double doubleValue() {
    return getPrimitiveValue(Number::doubleValue);
  }

  public byte byteValue() {
    return getPrimitiveValue(Number::byteValue);
  }

  public short shortValue() {
    return getPrimitiveValue(Number::shortValue);
  }

  public boolean lessThan(SELF other) {
    return this.compareTo(other) < 0;
  }

  public boolean lessThanEquals(SELF other) {
    return this.compareTo(other) <= 0;
  }

  public boolean greaterThan(SELF other) {
    return this.compareTo(other) > 0;
  }

  public boolean greaterThanEquals(SELF other) {
    return this.compareTo(other) >= 0;
  }

  protected <T> T getPrimitiveValue(Function<Number, T> mapper) {
    return getValue().map(mapper).orElseThrow(() -> new NullValueException(getClass()));
  }
}
