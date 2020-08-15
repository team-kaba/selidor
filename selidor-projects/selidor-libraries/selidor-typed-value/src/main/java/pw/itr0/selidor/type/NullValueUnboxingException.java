package pw.itr0.selidor.type;

/** 値として{@code null}を保持している{@link TypedValue}に対してプリミティブ型を返すメソッドを呼び出した場合に送出される例外です。 */
public class NullValueUnboxingException extends RuntimeException {

  public NullValueUnboxingException() {
    super();
  }

  public NullValueUnboxingException(String message) {
    super(message);
  }

  public NullValueUnboxingException(Class<?> klass) {
    this(klass.getSimpleName() + " is holding null.");
  }
}
