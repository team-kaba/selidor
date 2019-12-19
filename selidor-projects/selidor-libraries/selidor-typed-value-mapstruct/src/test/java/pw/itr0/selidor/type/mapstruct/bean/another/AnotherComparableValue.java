package pw.itr0.selidor.type.mapstruct.bean.another;

import java.time.ZoneOffset;
import pw.itr0.selidor.type.TypedComparable;

public class AnotherComparableValue extends TypedComparable<AnotherComparableValue, ZoneOffset> {
  public AnotherComparableValue(ZoneOffset value) throws IllegalArgumentException {
    super(value, true);
  }
}
