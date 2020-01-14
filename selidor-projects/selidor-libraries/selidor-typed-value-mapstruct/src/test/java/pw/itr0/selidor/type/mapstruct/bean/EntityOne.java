package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBigDecimalValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBigIntegerValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneByteValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneCharacterValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneComparableValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneCrid;
import pw.itr0.selidor.type.mapstruct.bean.one.OneDoubleValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneFloatValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneIntegerValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneLongId;
import pw.itr0.selidor.type.mapstruct.bean.one.OneLongValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneShortValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneStringValue;

@Data
class EntityOne {
  private OneCrid id;
  private OneLongId longId;
  private OneSomeValue some;
  private OneBigDecimalValue bigDecimal;
  private OneBigIntegerValue bigInteger;
  private OneBooleanValue primitiveBoolean =
      new OneBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private OneBooleanValue objectBoolean;
  private OneByteValue primitiveByte =
      new OneByteValue((byte) 0); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private OneByteValue objectByte;
  private OneCharacterValue primitiveCharacter =
      new OneCharacterValue('\0'); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private OneCharacterValue objectCharacter;
  private OneComparableValue comparable;
  private OneDoubleValue primitiveDouble =
      new OneDoubleValue(0d); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private OneDoubleValue objectDouble;
  private OneFloatValue primitiveFloat = new OneFloatValue(0f); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private OneFloatValue objectFloat;
  private OneIntegerValue primitiveInteger =
      new OneIntegerValue(0); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private OneIntegerValue objectInteger;
  private OneLongValue primitiveLong = new OneLongValue(0L); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private OneLongValue objectLong;
  private OneShortValue primitiveShort =
      new OneShortValue((short) 0); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private OneShortValue objectShort;
  private OneStringValue string;
  private ValueObjectOne child;
  private List<ValueObjectOne> children;

  static EntityOne nonNullValues() {
    final EntityOne entity = new EntityOne();
    entity.setId(new OneCrid(TestData.CRID));
    entity.setLongId(new OneLongId(LongId.from(TestData.LONG)));
    entity.setSome(new OneSomeValue(TestData.MONEY));
    entity.setBigDecimal(new OneBigDecimalValue(TestData.BIG_DECIMAL));
    entity.setBigInteger(new OneBigIntegerValue(TestData.BIG_INTEGER));
    entity.setPrimitiveBoolean(new OneBooleanValue(TestData.BOOLEAN));
    entity.setObjectBoolean(new OneBooleanValue(TestData.BOOLEAN));
    entity.setPrimitiveByte(new OneByteValue(TestData.BYTE));
    entity.setObjectByte(new OneByteValue(TestData.BYTE));
    entity.setPrimitiveCharacter(new OneCharacterValue(TestData.CHARACTER));
    entity.setObjectCharacter(new OneCharacterValue(TestData.CHARACTER));
    entity.setComparable(new OneComparableValue(TestData.ZONE_OFFSET));
    entity.setPrimitiveDouble(new OneDoubleValue(TestData.DOUBLE));
    entity.setObjectDouble(new OneDoubleValue(TestData.DOUBLE));
    entity.setPrimitiveFloat(new OneFloatValue(TestData.FLOAT));
    entity.setObjectFloat(new OneFloatValue(TestData.FLOAT));
    entity.setPrimitiveInteger(new OneIntegerValue(TestData.INTEGER));
    entity.setObjectInteger(new OneIntegerValue(TestData.INTEGER));
    entity.setPrimitiveLong(new OneLongValue(TestData.LONG));
    entity.setObjectLong(new OneLongValue(TestData.LONG));
    entity.setPrimitiveShort(new OneShortValue(TestData.SHORT));
    entity.setObjectShort(new OneShortValue(TestData.SHORT));
    entity.setString(new OneStringValue(TestData.STRING));
    entity.setChild(ValueObjectOne.nonNullValues());
    entity.setChildren(List.of(ValueObjectOne.nonNullValues(), ValueObjectOne.nonNullValues()));
    return entity;
  }
}
