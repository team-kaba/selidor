package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedInteger;

public interface TypedIntegerTranslator {
  default Integer mapTypedIntegerToInteger(TypedInteger<?> source) {
    return getValue(source);
  }

  default <T extends TypedInteger<T>> T mapIntegerToTypedInteger(
      Integer source, @TargetType Class<T> targetType) {
    return mapGeneric(source, Integer.class, targetType);
  }

  default <S extends TypedInteger<S>, T extends TypedInteger<T>> T mapBetweenTypedInteger(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), Integer.class, targetType);
  }
}
