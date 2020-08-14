package pw.itr0.selidor.type;

public abstract class TypedFloat<SELF extends TypedFloat<? super SELF>> extends TypedNumeric<SELF, Float> {
  protected TypedFloat(Float value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
