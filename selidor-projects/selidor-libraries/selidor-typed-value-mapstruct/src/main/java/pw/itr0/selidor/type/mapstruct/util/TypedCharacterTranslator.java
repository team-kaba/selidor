package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedCharacter;

public interface TypedCharacterTranslator {
  default Character mapTypedCharacterToCharacter(TypedCharacter<?> source) {
    return getValue(source);
  }

  default <T extends TypedCharacter<T>> T mapCharacterToTypedCharacter(
      Character source, @TargetType Class<T> targetType) {
    return mapGeneric(source, Character.class, targetType);
  }

  default <S extends TypedCharacter<S>, T extends TypedCharacter<T>> T mapBetweenTypedCharacter(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), Character.class, targetType);
  }
}
