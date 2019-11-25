package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class FormOne {
  private Money some;
  private boolean primitiveBoolean;
  private Boolean objectBoolean;
  private NestedFormOne child;
  private List<NestedFormOne> children;

  static FormOne nonNullValues() {
    final FormOne form = new FormOne();
    form.setSome(new Money(1));
    form.setPrimitiveBoolean(true);
    form.setObjectBoolean(Boolean.TRUE);
    form.setChild(NestedFormOne.nonNullValues());
    form.setChildren(List.of(NestedFormOne.nonNullValues(), NestedFormOne.nonNullValues()));
    return form;
  }
}
