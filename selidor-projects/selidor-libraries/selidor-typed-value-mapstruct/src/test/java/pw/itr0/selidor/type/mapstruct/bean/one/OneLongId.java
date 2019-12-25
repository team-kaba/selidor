package pw.itr0.selidor.type.mapstruct.bean.one;

import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.TypedLongId;

public class OneLongId extends TypedLongId<OneLongId> {
  public OneLongId(LongId value) {
    super(value, false);
  }
}
