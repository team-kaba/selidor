package pw.itr0.selidor.type.mapstruct.util;

import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.getValue;
import static pw.itr0.selidor.type.mapstruct.TypedValueUtil.mapGeneric;

import java.util.UUID;
import org.mapstruct.TargetType;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.type.TypedCrid;
import pw.itr0.selidor.type.mapstruct.CridStringToUUID;
import pw.itr0.selidor.type.mapstruct.UUIDToCridString;

public interface TypedCridTranslator {
  // Crid
  default <T extends TypedCrid<T>> T mapCridToTypedCrid(
      Crid source, @TargetType Class<T> targetType) {
    return mapGeneric(source, targetType);
  }

  default <T extends TypedCrid<T>> Crid mapTypedCridToCrid(T source) {
    return getValue(source);
  }

  // String
  default <T extends TypedCrid<T>> T mapStringToTypedCrid(
      String source, @TargetType Class<T> targetType) {
    if (source == null) {
      return null;
    }
    return mapGeneric(Crid.parse(source), targetType);
  }

  default <T extends TypedCrid<T>> String mapTypedCridToString(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().toString();
  }

  // UUID
  default <T extends TypedCrid<T>> T mapUuidToTypedCrid(
      UUID source, @TargetType Class<T> targetType) {
    if (source == null) {
      return null;
    }
    return mapGeneric(Crid.from(source), targetType);
  }

  default <T extends TypedCrid<T>> UUID mapTypedCridToUuid(T source) {
    if (source == null) {
      return null;
    }
    return source.getValue().uuid();
  }

  // Crid String <-> UUID
  @CridStringToUUID
  default UUID mapCridStringToUuid(String source) {
    if (source == null) {
      return null;
    }
    return Crid.parse(source).uuid();
  }

  @UUIDToCridString
  default String mapUuidToCridString(UUID source) {
    if (source == null) {
      return null;
    }
    return Crid.from(source).toString();
  }

  default <S extends TypedCrid<S>, T extends TypedCrid<T>> T mapBetweenTypedCrid(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), targetType);
  }
}
