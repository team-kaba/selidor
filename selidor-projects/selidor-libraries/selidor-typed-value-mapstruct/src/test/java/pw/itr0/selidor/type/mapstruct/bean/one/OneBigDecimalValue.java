package pw.itr0.selidor.type.mapstruct.bean.one;

import java.math.BigDecimal;
import pw.itr0.selidor.type.TypedBigDecimal;

public final class OneBigDecimalValue extends TypedBigDecimal<OneBigDecimalValue> {
  public OneBigDecimalValue(BigDecimal value) {
    super(value, false);
  }
}
