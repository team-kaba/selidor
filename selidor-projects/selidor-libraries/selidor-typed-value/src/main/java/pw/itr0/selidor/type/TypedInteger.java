package pw.itr0.selidor.type;

public abstract class TypedInteger<SELF extends TypedInteger<? super SELF>>
    extends TypedNumeric<SELF, Integer> {
  protected TypedInteger(Integer value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
