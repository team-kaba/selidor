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
    if (id == null) {
      return null;
    }
    return mapGeneric(id, typedValueClass);
  }

  default <T extends TypedLongId<T>> LongId mapTypedLongIdToLongId(T id) {
    return getValue(id);
  }

  // String
  default <T extends TypedLongId<T>> T mapStringToTypedLongId(
      String id, @TargetType Class<T> typedValueClass) {
    if (id == null) {
      return null;
    }
    return mapGeneric(LongId.parse(id), typedValueClass);
  }

  default <T extends TypedLongId<T>> String mapTypedLongIdToString(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().toString();
  }

  // Long
  default <T extends TypedLongId<T>> T mapLongToTypedLongId(
      Long id, @TargetType Class<T> typedValueClass) {
    if (id == null) {
      return null;
    }
    return mapGeneric(LongId.from(id), typedValueClass);
  }

  default <T extends TypedLongId<T>> Long mapTypedLongIdToLong(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().longValue();
  }

  default <S extends TypedLongId<S>, T extends TypedLongId<T>> T mapBetweenTypedLongId(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), targetType);
  }
}
