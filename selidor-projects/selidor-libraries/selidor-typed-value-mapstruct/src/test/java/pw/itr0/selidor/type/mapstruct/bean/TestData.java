package pw.itr0.selidor.type.mapstruct.bean;

import java.time.ZoneOffset;
import java.util.Random;
import java.util.UUID;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

class TestData {

  static final long LONG = new Random().nextLong();

  static final String STRING = "String values for tests. 髙島屋 ✒️ ⚒️" + LONG;

  static final boolean BOOLEAN = new Random().nextBoolean();

  static final Crid CRID = Crid.from(UUID.randomUUID());

  static final Money MONEY = new Money((int) LONG);

  static final ZoneOffset ZONE_OFFSET = ZoneOffset.ofHours(9);
}
