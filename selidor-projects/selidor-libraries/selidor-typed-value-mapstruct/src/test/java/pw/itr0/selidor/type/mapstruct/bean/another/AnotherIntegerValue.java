package pw.itr0.selidor.type.mapstruct.bean.another;

import pw.itr0.selidor.type.TypedInteger;

public final class AnotherIntegerValue extends TypedInteger<AnotherIntegerValue> {
  public AnotherIntegerValue(Integer value) {
    super(value, true);
  }
}
