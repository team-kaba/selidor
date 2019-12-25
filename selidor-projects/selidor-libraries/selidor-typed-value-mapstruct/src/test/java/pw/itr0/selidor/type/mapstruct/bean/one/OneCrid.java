package pw.itr0.selidor.type.mapstruct.bean.one;

import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.type.TypedCrid;

public class OneCrid extends TypedCrid<OneCrid> {
  public OneCrid(Crid value) {
    super(value, false);
  }
}
