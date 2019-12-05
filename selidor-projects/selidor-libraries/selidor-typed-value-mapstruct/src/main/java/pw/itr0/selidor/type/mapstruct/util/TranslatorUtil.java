package pw.itr0.selidor.type.mapstruct.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import pw.itr0.selidor.type.TypedValue;

public abstract class TranslatorUtil {

  public static <S, T extends TypedValue<S>> T mapGeneric(S source, Class<T> targetType) {
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

  public static <S, T extends TypedValue<S>> S getValue(T source) {
    return source != null ? source.getValue() : null;
  }
}
