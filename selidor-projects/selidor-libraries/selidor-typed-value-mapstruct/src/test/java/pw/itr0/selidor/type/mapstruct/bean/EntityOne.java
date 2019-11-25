package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class EntityOne {
  private OneSomeValue some;
  private OneBooleanValue primitiveBoolean =
      new OneBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private OneBooleanValue objectBoolean;
  private ValueObjectOne child;
  private List<ValueObjectOne> children;

  static EntityOne nonNullValues() {
    final EntityOne entity = new EntityOne();
    entity.setSome(new OneSomeValue(new Money(1)));
    entity.setPrimitiveBoolean(new OneBooleanValue(true));
    entity.setObjectBoolean(new OneBooleanValue(Boolean.TRUE));
    entity.setChild(ValueObjectOne.nonNullValues());
    entity.setChildren(List.of(ValueObjectOne.nonNullValues(), ValueObjectOne.nonNullValues()));
    return entity;
  }
}
