package pw.itr0.selidor.type;

public abstract class TypedByte<SELF extends TypedByte<? super SELF>>
    extends TypedNumeric<SELF, Byte> {

  protected TypedByte(Byte value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
