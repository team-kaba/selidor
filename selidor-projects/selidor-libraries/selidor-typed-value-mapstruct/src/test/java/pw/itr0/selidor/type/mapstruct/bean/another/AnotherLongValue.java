package pw.itr0.selidor.type.mapstruct.bean.another;

import pw.itr0.selidor.type.TypedLong;

public final class AnotherLongValue extends TypedLong<AnotherLongValue> {
  public AnotherLongValue(Long value) {
    super(value, true);
  }
}
