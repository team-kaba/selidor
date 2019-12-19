package pw.itr0.selidor.type.mapstruct.bean.another;

import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.type.TypedCrid;

public class AnotherCrid extends TypedCrid<AnotherCrid> {
  public AnotherCrid(Crid value) {
    super(value, true);
  }
}
