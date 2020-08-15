package pw.itr0.selidor.type;

public class NullValueException extends RuntimeException {

  public NullValueException() {
    super();
  }

  public NullValueException(String message) {
    super(message);
  }

  public NullValueException(Class<?> klass) {
    this(klass.getSimpleName() + " is holding null.");
  }
}
