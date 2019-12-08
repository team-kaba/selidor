package pw.itr0.selidor.type.mapstruct.bean.another;

import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.TypedLongId;

public class AnotherLongId extends TypedLongId<AnotherLongId> {
  public AnotherLongId(LongId value) {
    super(value);
  }
}
