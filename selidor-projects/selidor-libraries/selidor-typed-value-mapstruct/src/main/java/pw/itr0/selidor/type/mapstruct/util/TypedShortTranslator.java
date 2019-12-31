package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedShort;

public interface TypedShortTranslator {
  default Short mapTypedShortToShort(TypedShort<?> source) {
    return getValue(source);
  }

  default <T extends TypedShort<T>> T mapShortToTypedShort(
      Short source, @TargetType Class<T> targetType) {
    return mapGeneric(source, Short.class, targetType);
  }

  default <S extends TypedShort<S>, T extends TypedShort<T>> T mapBetweenTypedShort(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), Short.class, targetType);
  }
}
