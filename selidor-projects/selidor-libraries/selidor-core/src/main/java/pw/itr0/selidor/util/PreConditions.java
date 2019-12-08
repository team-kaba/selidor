package pw.itr0.selidor.util;

import java.util.function.Supplier;

public abstract class PreConditions {

  public static void requireNonNull(Object object, Supplier<String> message) {
    require(object != null, message);
  }

  public static void requireExactSize(byte[] bytes, int expected, Supplier<String> message) {
    require(bytes.length == expected, message);
  }

  public static void requireExactSize(String string, int expected, Supplier<String> message) {
    require(Character.codePointCount(string, 0, string.length()) == expected, message);
  }

  public static void requireLessThan(String string, String compare, Supplier<String> message) {
    require(string.compareTo(compare) < 0, message);
  }

  private static void require(boolean condition, Supplier<String> message) {
    if (!condition) {
      throw preConditionNotMet(message);
    }
  }

  private static IllegalArgumentException preConditionNotMet(Supplier<String> messageSupplier) {
    return new IllegalArgumentException(messageSupplier.get());
  }
}
