package pw.itr0.selidor.type.mapstruct.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

class TestData {

  static final BigDecimal BIG_DECIMAL =
      new BigDecimal(new BigInteger(10, new Random()), new Random().nextInt(10));

  static final BigInteger BIG_INTEGER = new BigInteger(10, new Random());

  static final boolean BOOLEAN = new Random().nextBoolean();

  static final byte BYTE = (byte) new Random().nextInt(Byte.MAX_VALUE);

  static final char CHARACTER = (char) new Random().nextInt();

  static final double DOUBLE = new Random().nextDouble();

  static final float FLOAT = new Random().nextFloat();

  static final int INTEGER = new Random().nextInt();

  static final long LONG = new Random().nextLong();

  static final short SHORT = (short) new Random().nextInt();

  static final String STRING = "String values for tests. 髙島屋 ✒️ ⚒️" + LONG;

  static final Crid CRID = Crid.from(UUID.randomUUID());

  static final Money MONEY = new Money((int) LONG);

  static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(9);
}
