package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedLong;

public interface TypedLongTranslator {
  default Long mapTypedLongToLong(TypedLong<?> source) {
    return getValue(source);
  }

  default <T extends TypedLong<T>> T mapLongToTypedLong(
      Long source, @TargetType Class<T> targetType) {
    return mapGeneric(source, Long.class, targetType);
  }

  default <S extends TypedLong<S>, T extends TypedLong<T>> T mapBetweenTypedLong(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), Long.class, targetType);
  }
}
