package pw.itr0.selidor.type;

public abstract class TypedShort<SELF extends TypedShort<? super SELF>>
    extends TypedNumeric<SELF, Short> {
  protected TypedShort(Short value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
