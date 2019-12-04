package pw.itr0.selidor.type;

import pw.itr0.selidor.identifier.random.LongId;

public abstract class TypedLongId<SELF extends TypedLongId<? super SELF>>
    extends TypedComparable<SELF, LongId> {
  protected TypedLongId(LongId value) {
    super(value);
  }

  public long longValue() {
    return getValue().longValue();
  }
}
