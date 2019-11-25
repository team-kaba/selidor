package pw.itr0.selidor.type.mapstruct;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.mapstruct.TargetType;
import pw.itr0.selidor.type.TypedBoolean;
import pw.itr0.selidor.type.TypedValue;

/**
 * {@link pw.itr0.selidor.type} に配置されている {@link TypedValue} の抽象クラスと基本クラスの間の変換を行うクラスです。
 *
 * <p>{@link TypedValue} 間での変換はできません。
 */
public class TypedValueTranslator {

  public Boolean map(TypedBoolean<?> typedValue) {
    if (typedValue == null) {
      return null;
    }
    return typedValue.getValue();
  }

  public <T extends TypedBoolean<T>> T map(Boolean rawValue, @TargetType Class<T> typedValueClass) {
    return mapGeneric(rawValue, typedValueClass);
  }

  public <T extends TypedBoolean<T>> T ident(T typedValue) {
    return typedValue;
  }

  <S, T extends TypedValue<S>> T mapGeneric(S rawValue, @TargetType Class<T> typedValueClass) {
    if (rawValue == null) {
      return null;
    }
    Constructor<T> constructor;
    Class<?> rawValueClass = rawValue.getClass();
    try {
      constructor = typedValueClass.getConstructor(rawValueClass);
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          String.format(
              "Failed to find a constructor of class [%s] with parameter [%s].",
              typedValueClass.getCanonicalName(), rawValueClass.getCanonicalName()),
          e);
    }
    try {
      return constructor.newInstance(rawValue);
    } catch (InvocationTargetException e) {
      throw new IllegalArgumentException(
          String.format(
              "Failed to instantiate class [%s] with parameter [%s](%s).",
              typedValueClass.getCanonicalName(),
              String.valueOf(rawValue),
              rawValueClass.getCanonicalName()),
          e);
    } catch (IllegalAccessException | InstantiationException e) {
      throw new IllegalStateException(
          String.format(
              "Failed to instantiate class [%s] with parameter [%s](%s).",
              typedValueClass.getCanonicalName(),
              String.valueOf(rawValue),
              rawValueClass.getCanonicalName()),
          e);
    }
  }
}
