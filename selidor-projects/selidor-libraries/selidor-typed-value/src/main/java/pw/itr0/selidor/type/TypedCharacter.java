package pw.itr0.selidor.type;

public abstract class TypedCharacter<SELF extends TypedCharacter<? super SELF>>
    extends TypedComparable<SELF, Character> {
  protected TypedCharacter(Character value, boolean nullFirst) {
    super(value, nullFirst);
  }

  public char charValue() {
    return getValue()
        .orElseThrow(
            () -> new IllegalStateException(getClass().getSimpleName() + " is holding null."));
  }
}
