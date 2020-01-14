package pw.itr0.selidor.type.mapstruct.bean;

import java.util.List;
import lombok.Data;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherBigDecimalValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherBigIntegerValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherByteValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherCharacterValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherComparableValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherCrid;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherDoubleValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherFloatValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherIntegerValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherLongId;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherLongValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherShortValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherStringValue;

@Data
class EntityAnother {
  private AnotherCrid id;
  private AnotherLongId longId;
  private AnotherSomeValue some;
  private AnotherBigDecimalValue bigDecimal;
  private AnotherBigIntegerValue bigInteger;
  private AnotherBooleanValue primitiveBoolean =
      new AnotherBooleanValue(false); // primitiveなのでデフォルト値がnullじゃなくてfalseになる。
  private AnotherBooleanValue objectBoolean;
  private AnotherByteValue primitiveByte =
      new AnotherByteValue((byte) 0); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private AnotherByteValue objectByte;
  private AnotherCharacterValue primitiveCharacter =
      new AnotherCharacterValue('\0'); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private AnotherCharacterValue objectCharacter;
  private AnotherComparableValue comparable;
  private AnotherDoubleValue primitiveDouble =
      new AnotherDoubleValue(0d); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private AnotherDoubleValue objectDouble;
  private AnotherFloatValue primitiveFloat =
      new AnotherFloatValue(0f); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private AnotherFloatValue objectFloat;
  private AnotherIntegerValue primitiveInteger =
      new AnotherIntegerValue(0); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private AnotherIntegerValue objectInteger;
  private AnotherLongValue primitiveLong =
      new AnotherLongValue(0L); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private AnotherLongValue objectLong;
  private AnotherShortValue primitiveShort =
      new AnotherShortValue((short) 0); // primitiveなのでデフォルト値がnullじゃなくて0になる。
  private AnotherShortValue objectShort;
  private AnotherStringValue string;
  private ValueObjectAnother child;
  private List<ValueObjectAnother> children;

  static EntityAnother nonNullValues() {
    final EntityAnother entity = new EntityAnother();
    entity.setId(new AnotherCrid(TestData.CRID));
    entity.setLongId(new AnotherLongId(LongId.from(TestData.LONG)));
    entity.setSome(new AnotherSomeValue(TestData.MONEY));
    entity.setBigDecimal(new AnotherBigDecimalValue(TestData.BIG_DECIMAL));
    entity.setBigInteger(new AnotherBigIntegerValue(TestData.BIG_INTEGER));
    entity.setPrimitiveBoolean(new AnotherBooleanValue(TestData.BOOLEAN));
    entity.setObjectBoolean(new AnotherBooleanValue(TestData.BOOLEAN));
    entity.setPrimitiveByte(new AnotherByteValue(TestData.BYTE));
    entity.setObjectByte(new AnotherByteValue(TestData.BYTE));
    entity.setPrimitiveCharacter(new AnotherCharacterValue(TestData.CHARACTER));
    entity.setObjectCharacter(new AnotherCharacterValue(TestData.CHARACTER));
    entity.setComparable(new AnotherComparableValue(TestData.ZONE_OFFSET));
    entity.setPrimitiveDouble(new AnotherDoubleValue(TestData.DOUBLE));
    entity.setObjectDouble(new AnotherDoubleValue(TestData.DOUBLE));
    entity.setPrimitiveFloat(new AnotherFloatValue(TestData.FLOAT));
    entity.setObjectFloat(new AnotherFloatValue(TestData.FLOAT));
    entity.setPrimitiveInteger(new AnotherIntegerValue(TestData.INTEGER));
    entity.setObjectInteger(new AnotherIntegerValue(TestData.INTEGER));
    entity.setPrimitiveLong(new AnotherLongValue(TestData.LONG));
    entity.setObjectLong(new AnotherLongValue(TestData.LONG));
    entity.setPrimitiveShort(new AnotherShortValue(TestData.SHORT));
    entity.setObjectShort(new AnotherShortValue(TestData.SHORT));
    entity.setString(new AnotherStringValue(TestData.STRING));
    entity.setChild(ValueObjectAnother.nonNullValues());
    entity.setChildren(
        List.of(ValueObjectAnother.nonNullValues(), ValueObjectAnother.nonNullValues()));
    return entity;
  }
}
