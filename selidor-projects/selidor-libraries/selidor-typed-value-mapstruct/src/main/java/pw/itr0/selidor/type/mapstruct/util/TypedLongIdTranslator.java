package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.TypedLongId;

public interface TypedLongIdTranslator {
  // LongId
  default <T extends TypedLongId<T>> T mapLongIdToTypedLongId(
      LongId id, @TargetType Class<T> typedValueClass) {
    return mapGeneric(id, LongId.class, typedValueClass);
  }

  default <T extends TypedLongId<T>> LongId mapTypedLongIdToLongId(T id) {
    return getValue(id);
  }

  // String
  default <T extends TypedLongId<T>> T mapStringToTypedLongId(
      String id, @TargetType Class<T> typedValueClass) {
    return mapGeneric(id != null ? LongId.parse(id) : null, LongId.class, typedValueClass);
  }

  default <T extends TypedLongId<T>> String mapTypedLongIdToString(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().map(LongId::toString).orElse(null);
  }

  // Long
  default <T extends TypedLongId<T>> T mapLongToTypedLongId(
      Long id, @TargetType Class<T> typedValueClass) {
    return mapGeneric(id != null ? LongId.from(id) : null, LongId.class, typedValueClass);
  }

  default <T extends TypedLongId<T>> Long mapTypedLongIdToLong(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().map(LongId::longValue).orElse(null);
  }

  default <S extends TypedLongId<S>, T extends TypedLongId<T>> T mapBetweenTypedLongId(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), LongId.class, targetType);
  }
}
