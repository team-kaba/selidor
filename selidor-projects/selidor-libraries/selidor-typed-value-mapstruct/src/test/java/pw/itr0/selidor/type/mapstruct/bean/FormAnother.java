package pw.itr0.selidor.type.mapstruct.bean;

import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class FormAnother {
  private UUID id;
  private Long longId;
  private Money some;
  private boolean primitiveBoolean;
  private Boolean objectBoolean;
  private ZoneOffset comparable;
  private String string;
  private NestedFormAnother child;
  private List<NestedFormAnother> children;

  static FormAnother nonNullValues() {
    final FormAnother form = new FormAnother();
    form.setId(TestData.CRID.uuid());
    form.setLongId(TestData.LONG);
    form.setSome(TestData.MONEY);
    form.setPrimitiveBoolean(TestData.BOOLEAN);
    form.setObjectBoolean(TestData.BOOLEAN);
    form.setComparable(TestData.ZONE_OFFSET);
    form.setString(TestData.STRING);
    form.setChild(NestedFormAnother.nonNullValues());
    form.setChildren(List.of(NestedFormAnother.nonNullValues(), NestedFormAnother.nonNullValues()));
    return form;
  }
}
