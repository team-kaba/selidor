package pw.itr0.selidor.type;

public abstract class TypedLong<SELF extends TypedLong<SELF>> extends TypedNumeric<SELF, Long> {
  protected TypedLong(Long value, boolean nullFirst) {
    super(value, nullFirst);
  }
}
