package pw.itr0.selidor.type.mapstruct.bean.another;

import pw.itr0.selidor.type.TypedFloat;

public final class AnotherFloatValue extends TypedFloat<AnotherFloatValue> {
  public AnotherFloatValue(Float value) {
    super(value, true);
  }
}
