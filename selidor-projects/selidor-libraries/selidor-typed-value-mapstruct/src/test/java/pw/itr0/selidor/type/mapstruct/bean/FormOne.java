package pw.itr0.selidor.type.mapstruct.bean;

import java.time.ZoneOffset;
import java.util.List;
import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class FormOne {
  private String id;
  private String longId;
  private Money some;
  private boolean primitiveBoolean;
  private Boolean objectBoolean;
  private ZoneOffset comparable;
  private String string;
  private NestedFormOne child;
  private List<NestedFormOne> children;

  static FormOne nonNullValues() {
    final FormOne form = new FormOne();
    form.setId(TestData.CRID.toString());
    form.setLongId(String.valueOf(TestData.LONG));
    form.setSome(TestData.MONEY);
    form.setPrimitiveBoolean(TestData.BOOLEAN);
    form.setObjectBoolean(TestData.BOOLEAN);
    form.setComparable(TestData.ZONE_OFFSET);
    form.setString(TestData.STRING);
    form.setChild(NestedFormOne.nonNullValues());
    form.setChildren(List.of(NestedFormOne.nonNullValues(), NestedFormOne.nonNullValues()));
    return form;
  }
}
