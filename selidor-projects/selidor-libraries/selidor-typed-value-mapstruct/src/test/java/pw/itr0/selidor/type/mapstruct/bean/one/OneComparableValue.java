package pw.itr0.selidor.type.mapstruct.bean.one;

import java.time.ZoneOffset;
import pw.itr0.selidor.type.TypedComparable;

public class OneComparableValue extends TypedComparable<OneComparableValue, ZoneOffset> {
  public OneComparableValue(ZoneOffset value) throws IllegalArgumentException {
    super(value, false);
  }
}
