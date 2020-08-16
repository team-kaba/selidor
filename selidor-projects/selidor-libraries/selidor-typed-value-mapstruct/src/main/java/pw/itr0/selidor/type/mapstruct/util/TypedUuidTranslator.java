package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import java.util.UUID;
import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedUuid;

public interface TypedUuidTranslator {
  default UUID mapTypedUuidToUuid(TypedUuid<?> source) {
    return getValue(source);
  }

  default <T extends TypedUuid<T>> T mapUuidToTypedUuid(
      UUID source, @TargetType Class<T> targetType) {
    return mapGeneric(source, UUID.class, targetType);
  }

  default <S extends TypedUuid<S>, T extends TypedUuid<T>> T mapBetweenTypedUuid(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), UUID.class, targetType);
  }
}
