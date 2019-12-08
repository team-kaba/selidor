package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedBoolean;

public interface TypedBooleanTranslator {
  default Boolean mapTypedBooleanToBoolean(TypedBoolean<?> source) {
    return getValue(source);
  }

  default <T extends TypedBoolean<T>> T mapBooleanToTypedBoolean(
      Boolean source, @TargetType Class<T> targetType) {
    return mapGeneric(source, targetType);
  }

  default <S extends TypedBoolean<S>, T extends TypedBoolean<T>> T mapBetweenTypedBoolean(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), targetType);
  }
}
