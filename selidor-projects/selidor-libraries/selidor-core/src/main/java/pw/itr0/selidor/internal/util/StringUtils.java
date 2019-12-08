package pw.itr0.selidor.internal.util;

public abstract class StringUtils {

  public static boolean hasText(String s) {
    return !isEmpty(s) && containsNonWhiteSpace(s);
  }

  public static boolean isEmpty(String s) {
    return s == null || s.isEmpty();
  }

  private static boolean containsNonWhiteSpace(String s) {
    return s.codePoints().anyMatch(c -> !Character.isWhitespace(c));
  }
}
