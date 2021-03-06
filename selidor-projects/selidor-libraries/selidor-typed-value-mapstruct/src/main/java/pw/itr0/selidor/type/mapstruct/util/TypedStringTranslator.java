package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedString;

public interface TypedStringTranslator {
  default String mapTypedStringToString(TypedString<?> source) {
    return getValue(source);
  }

  default <T extends TypedString<T>> T mapStringToTypedString(
      String source, @TargetType Class<T> targetType) {
    return mapGeneric(source, String.class, targetType);
  }

  default <S extends TypedString<S>, T extends TypedString<T>> T mapBetweenTypedString(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), String.class, targetType);
  }
}
