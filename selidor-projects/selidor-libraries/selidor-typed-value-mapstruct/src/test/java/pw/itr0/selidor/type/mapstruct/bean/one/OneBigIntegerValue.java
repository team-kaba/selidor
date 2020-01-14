package pw.itr0.selidor.type.mapstruct.bean.one;

import java.math.BigInteger;
import pw.itr0.selidor.type.TypedBigInteger;

public final class OneBigIntegerValue extends TypedBigInteger<OneBigIntegerValue> {
  public OneBigIntegerValue(BigInteger value) {
    super(value, false);
  }
}
