package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedDouble;

public interface TypedDoubleTranslator {
  default Double mapTypedDoubleToDouble(TypedDouble<?> source) {
    return getValue(source);
  }

  default <T extends TypedDouble<T>> T mapDoubleToTypedDouble(
      Double source, @TargetType Class<T> targetType) {
    return mapGeneric(source, Double.class, targetType);
  }

  default <S extends TypedDouble<S>, T extends TypedDouble<T>> T mapBetweenTypedDouble(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), Double.class, targetType);
  }
}
