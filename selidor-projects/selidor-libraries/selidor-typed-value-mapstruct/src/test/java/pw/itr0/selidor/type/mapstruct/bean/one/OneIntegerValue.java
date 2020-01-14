package pw.itr0.selidor.type.mapstruct.bean.one;

import pw.itr0.selidor.type.TypedInteger;

public final class OneIntegerValue extends TypedInteger<OneIntegerValue> {
  public OneIntegerValue(Integer value) {
    super(value, false);
  }
}
