package pw.itr0.selidor.type;

import java.time.Instant;
import java.util.UUID;
import pw.itr0.selidor.identifier.crid.Crid;

public abstract class TypedCrid<SELF extends TypedCrid<? super SELF>>
    extends TypedComparable<SELF, Crid> {
  protected TypedCrid(Crid value) {
    super(value);
  }

  public Instant timestamp() {
    return getValue().timestamp();
  }

  public byte[] randomness() {
    return getValue().randomness();
  }

  public UUID uuid() {
    return getValue().uuid();
  }
}
