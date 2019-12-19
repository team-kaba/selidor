package pw.itr0.selidor.type.mapstruct;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import pw.itr0.selidor.type.TypedValue;

public abstract class TypedValueUtil {

  public static <S, T extends TypedValue<S>> T mapGeneric(S source, Class<S> sourceType, Class<T> targetType) {
    Constructor<T> constructor;
    constructor = getConstructor(sourceType, targetType);
    return newInstance(source, targetType, constructor, sourceType);
  }

  public static <S, T extends TypedValue<S>> S getValue(T source) {
    return source != null ? source.getValue() : null;
  }

  private static <S, T extends TypedValue<S>> T newInstance(
      S source, Class<T> targetType, Constructor<T> constructor, Class<?> sourceType) {
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

  private static <S, T extends TypedValue<S>> Constructor<T> getConstructor(
      Class<?> sourceType, Class<T> targetType) {
    Constructor<T> constructor;
    try {
      constructor = targetType.getConstructor(sourceType);
    } catch (NoSuchMethodException e) {
      throw new IllegalArgumentException(
          String.format(
              "Failed to find a constructor of class [%s] with parameter [%s].",
              targetType.getCanonicalName(), sourceType.getCanonicalName()),
          e);
    }
    return constructor;
  }
}
