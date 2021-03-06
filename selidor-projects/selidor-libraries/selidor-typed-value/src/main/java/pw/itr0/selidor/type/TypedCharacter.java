package pw.itr0.selidor.type;

public abstract class TypedCharacter<SELF extends TypedCharacter<? super SELF>>
    extends TypedComparable<SELF, Character> {

  protected TypedCharacter(Character value, boolean nullFirst) {
    super(value, nullFirst);
  }

  public char charValue() throws NullValueUnboxingException {
    return getValue().orElseThrow(() -> new NullValueUnboxingException(getClass()));
  }
}
