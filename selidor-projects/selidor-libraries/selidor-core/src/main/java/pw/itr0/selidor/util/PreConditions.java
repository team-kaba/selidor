package pw.itr0.selidor.util;

import java.util.Objects;
import java.util.function.Supplier;

public abstract class PreConditions {

  public static void requireNonNull(Object object, Supplier<String> message) {
    if (object == null) {
      throw preConditionNotMet(message, () -> "object must not be null.");
    }
  }

  public static void requireExactSize(byte[] bytes, int expected, Supplier<String> message) {
    final int actual = bytes.length;
    if (actual != expected) {
      throw preConditionNotMet(
          message,
          () ->
              "byte array must be exactly " + expected + "bytes. actual size is [" + actual + "]");
    }
  }

  public static void requireExactSize(String string, int expected, Supplier<String> message) {
    final int actual = string.length();
    if (Character.codePointCount(string, 0, actual) != expected) {
      throw preConditionNotMet(
          message,
          () ->
              "string must be exactly "
                  + expected
                  + " chars long. actual size is ["
                  + actual
                  + "]");
    }
  }

  public static void requireLessThan(String string, String compare, Supplier<String> message) {
    if (compare.compareTo(string) < 0) {
      throw preConditionNotMet(
          message, () -> "string must not exceed '" + compare + "'. string=[" + string + "]");
    }
  }

  private static IllegalArgumentException preConditionNotMet(
      Supplier<String> messageSupplier, Supplier<String> defaultValue) {
    return messageSupplier != null
        ? new IllegalArgumentException(
            Objects.requireNonNullElseGet(messageSupplier.get(), defaultValue))
        : new IllegalArgumentException();
  }
}
