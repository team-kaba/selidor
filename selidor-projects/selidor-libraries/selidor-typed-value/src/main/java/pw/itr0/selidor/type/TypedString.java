package pw.itr0.selidor.type;

public abstract class TypedString<SELF extends TypedString<? super SELF>>
    extends TypedComparable<SELF, String> {
  protected TypedString(String value) {
    super(value);
  }

  public boolean isEmpty() {
    return getValue().isEmpty();
  }

  public boolean hasValue() {
    return !isEmpty();
  }
}
