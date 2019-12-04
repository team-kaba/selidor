package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneComparableValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneCrid;
import pw.itr0.selidor.type.mapstruct.bean.one.OneLongId;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneStringValue;

@Data
class EntityOne {
  private OneCrid id;
  private OneLongId longId;
  private OneSomeValue some;
  private OneBooleanValue primitiveBoolean =
      new OneBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private OneBooleanValue objectBoolean;
  private OneComparableValue comparable;
  private OneStringValue string;
  private ValueObjectOne child;
  private List<ValueObjectOne> children;

  static EntityOne nonNullValues() {
    final EntityOne entity = new EntityOne();
    entity.setId(new OneCrid(TestData.CRID));
    entity.setLongId(new OneLongId(LongId.from(TestData.LONG)));
    entity.setSome(new OneSomeValue(TestData.MONEY));
    entity.setPrimitiveBoolean(new OneBooleanValue(TestData.BOOLEAN));
    entity.setObjectBoolean(new OneBooleanValue(TestData.BOOLEAN));
    entity.setComparable(new OneComparableValue(TestData.ZONE_OFFSET));
    entity.setString(new OneStringValue(TestData.STRING));
    entity.setChild(ValueObjectOne.nonNullValues());
    entity.setChildren(List.of(ValueObjectOne.nonNullValues(), ValueObjectOne.nonNullValues()));
    return entity;
  }
}
