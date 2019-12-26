package pw.itr0.selidor.type;

import java.math.BigDecimal;

public abstract class TypedBigDecimal<SELF extends TypedBigDecimal<? super SELF>>
    extends TypedNumeric<SELF, BigDecimal> {
  protected TypedBigDecimal(BigDecimal value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
