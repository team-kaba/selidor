package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import java.math.BigDecimal;
import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedBigDecimal;

public interface TypedBigDecimalTranslator {
  default BigDecimal mapTypedBigDecimalToBigDecimal(TypedBigDecimal<?> source) {
    return getValue(source);
  }

  default <T extends TypedBigDecimal<T>> T mapBigDecimalToTypedBigDecimal(
      BigDecimal source, @TargetType Class<T> targetType) {
    return mapGeneric(source, BigDecimal.class, targetType);
  }

  default String mapTypedBigDecimalToString(TypedBigDecimal<?> source) {
    BigDecimal value = getValue(source);
    return value == null ? null : value.toPlainString();
  }

  default <T extends TypedBigDecimal<T>> T mapStringToTypedBigDecimal(
      String source, @TargetType Class<T> targetType) {
    return mapGeneric(new BigDecimal(source), BigDecimal.class, targetType);
  }

  default <S extends TypedBigDecimal<S>, T extends TypedBigDecimal<T>> T mapBetweenTypedBigDecimal(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), BigDecimal.class, targetType);
  }
}
