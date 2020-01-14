package pw.itr0.selidor.type;

public abstract class TypedDouble<SELF extends TypedDouble<SELF>>
    extends TypedNumeric<SELF, Double> {
  protected TypedDouble(Double value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
