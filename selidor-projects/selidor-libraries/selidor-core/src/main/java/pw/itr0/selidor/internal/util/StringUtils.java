package pw.itr0.selidor.internal.util;

public abstract class StringUtils {

  public static boolean isEmpty(String s) {
    return s == null || s.isEmpty();
  }

  public static boolean hasText(String s) {
    return !isEmpty(s) && !s.isBlank();
  }
}
