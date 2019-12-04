package pw.itr0.selidor.type.mapstruct.bean;

import lombok.Data;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class NestedFormOne {
  private Crid id;
  private Money some;
  private boolean primitiveBoolean;
  private Boolean objectBoolean;

  static NestedFormOne nonNullValues() {
    final NestedFormOne form = new NestedFormOne();
    form.setSome(new Money(1));
    form.setPrimitiveBoolean(true);
    form.setObjectBoolean(Boolean.TRUE);
    return form;
  }
}
