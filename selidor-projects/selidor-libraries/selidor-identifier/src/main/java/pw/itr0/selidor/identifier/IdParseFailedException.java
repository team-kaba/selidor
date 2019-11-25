package pw.itr0.selidor.identifier;

@SuppressWarnings("unused")
public class IdParseFailedException extends RuntimeException {

  public IdParseFailedException(String message) {
    super(message);
  }

  public IdParseFailedException(String message, Throwable cause) {
    super(message, cause);
  }
}
