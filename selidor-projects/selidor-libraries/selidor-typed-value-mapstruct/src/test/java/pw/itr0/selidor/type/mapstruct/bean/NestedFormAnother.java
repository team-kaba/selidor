package pw.itr0.selidor.type.mapstruct.bean;

import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class NestedFormAnother {
  private Money some;
  private boolean primitiveBoolean;
  private Boolean objectBoolean;

  static NestedFormAnother nonNullValues() {
    final NestedFormAnother form = new NestedFormAnother();
    form.setSome(new Money(1));
    form.setPrimitiveBoolean(true);
    form.setObjectBoolean(Boolean.TRUE);
    return form;
  }
}
