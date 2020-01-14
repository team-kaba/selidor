package pw.itr0.selidor.type.mapstruct.bean.another;

import pw.itr0.selidor.type.TypedDouble;

public final class AnotherDoubleValue extends TypedDouble<AnotherDoubleValue> {
  public AnotherDoubleValue(Double value) {
    super(value, true);
  }
}
