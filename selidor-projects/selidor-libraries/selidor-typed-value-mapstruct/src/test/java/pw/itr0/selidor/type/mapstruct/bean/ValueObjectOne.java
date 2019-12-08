package pw.itr0.selidor.type.mapstruct.bean;

import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneCrid;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class ValueObjectOne {
  private OneCrid id;
  private OneSomeValue some;
  private OneBooleanValue primitiveBoolean =
      new OneBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private OneBooleanValue objectBoolean;

  static ValueObjectOne nonNullValues() {
    final ValueObjectOne entity = new ValueObjectOne();
    entity.setSome(new OneSomeValue(new Money(1)));
    entity.setPrimitiveBoolean(new OneBooleanValue(true));
    entity.setObjectBoolean(new OneBooleanValue(Boolean.TRUE));
    return entity;
  }
}
