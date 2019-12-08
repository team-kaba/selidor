package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherComparableValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherCrid;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherLongId;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherStringValue;

@Data
class EntityAnother {
  private AnotherCrid id;
  private AnotherLongId longId;
  private AnotherSomeValue some;
  private AnotherBooleanValue primitiveBoolean =
      new AnotherBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private AnotherBooleanValue objectBoolean;
  private AnotherComparableValue comparable;
  private AnotherStringValue string;
  private ValueObjectAnother child;
  private List<ValueObjectAnother> children;

  static EntityAnother nonNullValues() {
    final EntityAnother entity = new EntityAnother();
    entity.setId(new AnotherCrid(TestData.CRID));
    entity.setLongId(new AnotherLongId(LongId.from(TestData.LONG)));
    entity.setSome(new AnotherSomeValue(TestData.MONEY));
    entity.setPrimitiveBoolean(new AnotherBooleanValue(TestData.BOOLEAN));
    entity.setObjectBoolean(new AnotherBooleanValue(TestData.BOOLEAN));
    entity.setComparable(new AnotherComparableValue(TestData.ZONE_OFFSET));
    entity.setString(new AnotherStringValue(TestData.STRING));
    entity.setChild(ValueObjectAnother.nonNullValues());
    entity.setChildren(
        List.of(ValueObjectAnother.nonNullValues(), ValueObjectAnother.nonNullValues()));
    return entity;
  }
}
