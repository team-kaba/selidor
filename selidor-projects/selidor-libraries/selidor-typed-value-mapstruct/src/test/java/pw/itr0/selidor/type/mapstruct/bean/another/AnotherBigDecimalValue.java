package pw.itr0.selidor.type.mapstruct.bean.another;

import java.math.BigDecimal;
import pw.itr0.selidor.type.TypedBigDecimal;

public final class AnotherBigDecimalValue extends TypedBigDecimal<AnotherBigDecimalValue> {
  public AnotherBigDecimalValue(BigDecimal value) {
    super(value, true);
  }
}
