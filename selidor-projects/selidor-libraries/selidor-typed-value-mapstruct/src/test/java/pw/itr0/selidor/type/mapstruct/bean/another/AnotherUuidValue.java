package pw.itr0.selidor.type.mapstruct.bean.another;

import java.util.UUID;
import pw.itr0.selidor.type.TypedUuid;

public class AnotherUuidValue extends TypedUuid<AnotherUuidValue> {

  public AnotherUuidValue(UUID value) {
    super(value, false);
  }
}
