package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import java.math.BigInteger;
import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedBigInteger;

public interface TypedBigIntegerTranslator {
  default BigInteger mapTypedBigIntegerToBigInteger(TypedBigInteger<?> source) {
    return getValue(source);
  }

  default <T extends TypedBigInteger<T>> T mapBigIntegerToTypedBigInteger(
      BigInteger source, @TargetType Class<T> targetType) {
    return mapGeneric(source, BigInteger.class, targetType);
  }

  default <S extends TypedBigInteger<S>, T extends TypedBigInteger<T>> T mapBetweenTypedBigInteger(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), BigInteger.class, targetType);
  }
}
