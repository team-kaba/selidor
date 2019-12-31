package pw.itr0.selidor.type.mapstruct.bean.another;

import java.math.BigInteger;
import pw.itr0.selidor.type.TypedBigInteger;

public final class AnotherBigIntegerValue extends TypedBigInteger<AnotherBigIntegerValue> {
  public AnotherBigIntegerValue(BigInteger value) {
    super(value, true);
  }
}
