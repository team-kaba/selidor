package pw.itr0.selidor.type;

import java.math.BigInteger;

public abstract class TypedBigInteger<SELF extends TypedBigInteger<? super SELF>>
    extends TypedNumeric<SELF, BigInteger> {
  protected TypedBigInteger(BigInteger value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
