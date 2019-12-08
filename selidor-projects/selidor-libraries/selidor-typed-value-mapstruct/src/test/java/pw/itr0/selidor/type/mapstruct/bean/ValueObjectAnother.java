package pw.itr0.selidor.type.mapstruct.bean;

import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherCrid;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class ValueObjectAnother {
  private AnotherCrid id;
  private AnotherSomeValue some;
  private AnotherBooleanValue primitiveBoolean =
      new AnotherBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private AnotherBooleanValue objectBoolean;

  static ValueObjectAnother nonNullValues() {
    final ValueObjectAnother entity = new ValueObjectAnother();
    entity.setSome(new AnotherSomeValue(new Money(1)));
    entity.setPrimitiveBoolean(new AnotherBooleanValue(true));
    entity.setObjectBoolean(new AnotherBooleanValue(Boolean.TRUE));
    return entity;
  }
}
