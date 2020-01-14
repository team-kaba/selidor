package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedByte;

public interface TypedByteTranslator {
  default Byte mapTypedByteToByte(TypedByte<?> source) {
    return getValue(source);
  }

  default <T extends TypedByte<T>> T mapByteToTypedByte(
      Byte source, @TargetType Class<T> targetType) {
    return mapGeneric(source, Byte.class, targetType);
  }

  default <S extends TypedByte<S>, T extends TypedByte<T>> T mapBetweenTypedByte(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), Byte.class, targetType);
  }
}
