package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class EntityAnother {
  private AnotherSomeValue some;
  private AnotherBooleanValue primitiveBoolean =
      new AnotherBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private AnotherBooleanValue objectBoolean;
  private ValueObjectAnother child;
  private List<ValueObjectAnother> children;

  static EntityAnother nonNullValues() {
    final EntityAnother entity = new EntityAnother();
    entity.setSome(new AnotherSomeValue(new Money(1)));
    entity.setPrimitiveBoolean(new AnotherBooleanValue(true));
    entity.setObjectBoolean(new AnotherBooleanValue(Boolean.TRUE));
    entity.setChild(ValueObjectAnother.nonNullValues());
    entity.setChildren(
        List.of(ValueObjectAnother.nonNullValues(), ValueObjectAnother.nonNullValues()));
    return entity;
  }
}
