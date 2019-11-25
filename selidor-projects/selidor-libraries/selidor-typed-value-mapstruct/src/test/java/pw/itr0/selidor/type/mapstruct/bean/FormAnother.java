package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class FormAnother {
  private Money some;
  private boolean primitiveBoolean;
  private Boolean objectBoolean;
  private NestedFormAnother child;
  private List<NestedFormAnother> children;

  static FormAnother nonNullValues() {
    final FormAnother form = new FormAnother();
    form.setSome(new Money(1));
    form.setPrimitiveBoolean(true);
    form.setObjectBoolean(Boolean.TRUE);
    form.setChild(NestedFormAnother.nonNullValues());
    form.setChildren(List.of(NestedFormAnother.nonNullValues(), NestedFormAnother.nonNullValues()));
    return form;
  }
}
