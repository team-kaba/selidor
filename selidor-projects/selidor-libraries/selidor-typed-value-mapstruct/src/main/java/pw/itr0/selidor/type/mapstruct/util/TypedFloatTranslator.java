package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedFloat;

public interface TypedFloatTranslator {
  default Float mapTypedFloatToFloat(TypedFloat<?> source) {
    return getValue(source);
  }

  default <T extends TypedFloat<T>> T mapFloatToTypedFloat(
      Float source, @TargetType Class<T> targetType) {
    return mapGeneric(source, Float.class, targetType);
  }

  default <S extends TypedFloat<S>, T extends TypedFloat<T>> T mapBetweenTypedFloat(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), Float.class, targetType);
  }
}
