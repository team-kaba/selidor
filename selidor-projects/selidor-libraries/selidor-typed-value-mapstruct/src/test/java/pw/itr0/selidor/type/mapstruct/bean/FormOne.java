package pw.itr0.selidor.type.mapstruct.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import lombok.Data;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

@Data
class FormOne {
  private String id;
  private String longId;
  private Money some;
  private BigDecimal bigDecimal;
  private BigInteger bigInteger;
  private boolean primitiveBoolean;
  private Boolean objectBoolean;
  private byte primitiveByte;
  private Byte objectByte;
  private char primitiveCharacter;
  private Character objectCharacter;
  private ZoneOffset comparable;
  private double primitiveDouble;
  private Double objectDouble;
  private float primitiveFloat;
  private Float objectFloat;
  private int primitiveInteger;
  private Integer objectInteger;
  private long primitiveLong;
  private Long objectLong;
  private short primitiveShort;
  private Short objectShort;
  private String string;
  private NestedFormOne child;
  private List<NestedFormOne> children;
  private UUID uuid;

  static FormOne nonNullValues() {
    final FormOne form = new FormOne();
    form.setId(TestData.CRID.toString());
    form.setLongId(String.valueOf(TestData.LONG));
    form.setSome(TestData.MONEY);
    form.setBigDecimal(TestData.BIG_DECIMAL);
    form.setBigInteger(TestData.BIG_INTEGER);
    form.setPrimitiveBoolean(TestData.BOOLEAN);
    form.setObjectBoolean(TestData.BOOLEAN);
    form.setPrimitiveByte(TestData.BYTE);
    form.setObjectByte(TestData.BYTE);
    form.setPrimitiveCharacter(TestData.CHARACTER);
    form.setObjectCharacter(TestData.CHARACTER);
    form.setComparable(TestData.ZONE_OFFSET);
    form.setPrimitiveDouble(TestData.DOUBLE);
    form.setObjectDouble(TestData.DOUBLE);
    form.setPrimitiveFloat(TestData.FLOAT);
    form.setObjectFloat(TestData.FLOAT);
    form.setPrimitiveInteger(TestData.INTEGER);
    form.setObjectInteger(TestData.INTEGER);
    form.setPrimitiveLong(TestData.LONG);
    form.setObjectLong(TestData.LONG);
    form.setPrimitiveShort(TestData.SHORT);
    form.setObjectShort(TestData.SHORT);
    form.setString(TestData.STRING);
    form.setChild(NestedFormOne.nonNullValues());
    form.setChildren(List.of(NestedFormOne.nonNullValues(), NestedFormOne.nonNullValues()));
    form.setUuid(TestData.RANDOM_UUID);
    return form;
  }
}
