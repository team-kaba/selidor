package pw.itr0.selidor.type.mapstruct.bean.one;

import java.util.UUID;
import pw.itr0.selidor.type.TypedUuid;

public final class OneUuidValue extends TypedUuid<OneUuidValue> {

  public OneUuidValue(UUID value) {
    super(value, true);
  }
}
