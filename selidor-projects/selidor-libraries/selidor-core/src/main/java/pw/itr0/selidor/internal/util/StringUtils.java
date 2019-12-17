package pw.itr0.selidor.internal.util;

public abstract class StringUtils {

  public static boolean isEmpty(String s) {
    return s == null || s.isEmpty();
  }

  public static boolean isNotEmpty(String s) {
    return s != null && !s.isEmpty();
  }

  public static boolean isBlank(String s) {
    return isEmpty(s) || s.isBlank();
  }

  public static boolean isNotBlank(String s) {
    return !isEmpty(s) && !s.isBlank();
  }
}
