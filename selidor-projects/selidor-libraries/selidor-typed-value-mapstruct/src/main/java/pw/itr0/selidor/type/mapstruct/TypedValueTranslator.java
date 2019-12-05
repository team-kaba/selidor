package pw.itr0.selidor.type.mapstruct;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import org.mapstruct.TargetType;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.TypedBoolean;
import pw.itr0.selidor.type.TypedCrid;
import pw.itr0.selidor.type.TypedLongId;
import pw.itr0.selidor.type.TypedString;
import pw.itr0.selidor.type.TypedValue;

/**
 * {@link pw.itr0.selidor.type} に配置されている {@link TypedValue} の抽象クラスと基本クラスの間の変換を行うクラスです。
 */
public class TypedValueTranslator {

  ////////////////////////////////////////////////////////////////////////////////////
  // Basic types
  ////////////////////////////////////////////////////////////////////////////////////

  public UUID mapStringToUuid(String source) {
    return source != null ? UUID.fromString(source) : null;
  }

  public String mapUuidToString(UUID source) {
    return source != null ? source.toString() : null;
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // Typed basic types
  ////////////////////////////////////////////////////////////////////////////////////

  // ------------------------------------------------
  // -- TypedBoolean
  // ------------------------------------------------

  public Boolean mapTypedBooleanToBoolean(TypedBoolean<?> source) {
    return getValue(source);
  }

  public <T extends TypedBoolean<T>> T mapBooleanToTypedBoolean(
      Boolean source, @TargetType Class<T> targetType) {
    return mapGeneric(source, targetType);
  }

  public <S extends TypedBoolean<S>, T extends TypedBoolean<T>> T mapBetweenTypedBoolean(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), targetType);
  }

  // ------------------------------------------------
  // -- TypedString
  // ------------------------------------------------

  public String mapTypedStringToString(TypedString<?> source) {
    return getValue(source);
  }

  public <T extends TypedString<T>> T mapStringToTypedString(
      String source, @TargetType Class<T> targetType) {
    return mapGeneric(source, targetType);
  }

  public <S extends TypedString<S>, T extends TypedString<T>> T mapBetweenTypedString(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), targetType);
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // Identifier
  ////////////////////////////////////////////////////////////////////////////////////

  // ------------------------------------------------
  // -- TypedCrid
  // ------------------------------------------------

  // Crid

  public <T extends TypedCrid<T>> T mapCridToTypedCrid(
      Crid source, @TargetType Class<T> targetType) {
    return mapGeneric(source, targetType);
  }

  public <T extends TypedCrid<T>> Crid mapTypedCridToCrid(T source) {
    return getValue(source);
  }

  // String

  public <T extends TypedCrid<T>> T mapStringToTypedCrid(
      String source, @TargetType Class<T> targetType) {
    if (source == null) {
      return null;
    }
    return mapGeneric(Crid.parse(source), targetType);
  }

  public <T extends TypedCrid<T>> String mapTypedCridToString(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().toString();
  }

  // UUID

  public <T extends TypedCrid<T>> T mapUuidToTypedCrid(
      UUID source, @TargetType Class<T> targetType) {
    if (source == null) {
      return null;
    }
    return mapGeneric(Crid.from(source), targetType);
  }

  public <T extends TypedCrid<T>> UUID mapTypedCridToUuid(T source) {
    if (source == null) {
      return null;
    }
    return source.getValue().uuid();
  }

  // Crid String <-> UUID

  @CridStringToUUID
  public UUID mapCridStringToUuid(String source) {
    if (source == null) {
      return null;
    }
    return Crid.parse(source).uuid();
  }

  @UUIDToCridString
  public String mapUuidToCridString(UUID source) {
    if (source == null) {
      return null;
    }
    return Crid.from(source).toString();
  }

  public <S extends TypedCrid<S>, T extends TypedCrid<T>> T mapBetweenTypedCrid(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), targetType);
  }

  // ------------------------------------------------
  // -- TypedLongId
  // ------------------------------------------------

  // LongId

  public <T extends TypedLongId<T>> T mapLongIdToTypedLongId(
      LongId id, @TargetType Class<T> typedValueClass) {
    if (id == null) {
      return null;
    }
    return mapGeneric(id, typedValueClass);
  }

  public <T extends TypedLongId<T>> LongId mapTypedLongIdToLongId(T id) {
    return getValue(id);
  }

  // String

  public <T extends TypedLongId<T>> T mapStringToTypedLongId(
      String id, @TargetType Class<T> typedValueClass) {
    if (id == null) {
      return null;
    }
    return mapGeneric(LongId.parse(id), typedValueClass);
  }

  public <T extends TypedLongId<T>> String mapTypedLongIdToString(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().toString();
  }

  // Long

  public <T extends TypedLongId<T>> T mapLongToTypedLongId(
      Long id, @TargetType Class<T> typedValueClass) {
    if (id == null) {
      return null;
    }
    return mapGeneric(LongId.from(id), typedValueClass);
  }

  public <T extends TypedLongId<T>> Long mapTypedLongIdToLong(T id) {
    if (id == null) {
      return null;
    }
    return id.getValue().longValue();
  }

  public <S extends TypedLongId<S>, T extends TypedLongId<T>> T mapBetweenTypedLongId(
      S source, @TargetType Class<T> targetType) {
    return mapGeneric(getValue(source), targetType);
  }

  ////////////////////////////////////////////////////////////////////////////////////
  // Generic mapper
  ////////////////////////////////////////////////////////////////////////////////////

  static <S, T extends TypedValue<S>> T mapGeneric(S source, Class<T> targetType) {
    if (source == null) {
      return null;
    }
    Constructor<T> constructor;
    Class<?> sourceType = source.getClass();
    try {
      constructor = targetType.getConstructor(sourceType);
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          String.format(
              "Failed to find a constructor of class [%s] with parameter [%s].",
              targetType.getCanonicalName(), sourceType.getCanonicalName()),
          e);
    }
    try {
      return constructor.newInstance(source);
    } catch (InvocationTargetException e) {
      throw new IllegalArgumentException(
          String.format(
              "Failed to instantiate class [%s] with parameter [%s](%s).",
              targetType.getCanonicalName(), source, sourceType.getCanonicalName()),
          e);
    } catch (IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException(
          String.format(
              "Failed to instantiate class [%s] with parameter [%s](%s).",
              targetType.getCanonicalName(), source, sourceType.getCanonicalName()),
          e);
    }
  }

  private static <S, T extends TypedValue<S>> S getValue(T source) {
    return source != null ? source.getValue() : null;
  }
}
