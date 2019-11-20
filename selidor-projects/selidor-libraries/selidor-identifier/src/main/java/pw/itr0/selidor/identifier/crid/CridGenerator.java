package pw.itr0.selidor.identifier.crid;

import java.time.Clock;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import pw.itr0.selidor.identifier.Id128Generator;
import pw.itr0.selidor.identifier.IdParseFailedException;
import pw.itr0.selidor.util.ByteArrayUtil;

public class CridGenerator implements Id128Generator<Crid> {

  private final Clock clock;
  private final Random random;

  public CridGenerator(Clock clock, Random random) {
    this.clock = clock;
    this.random = random;
  }

  public Crid next() {
    final byte[] bytes = new byte[10];
    random.nextBytes(bytes);
    return new Crid(clock.millis(), bytes);
  }

  public Crid parse(String value) throws IdParseFailedException {
    try {
      return new Crid(value);
    } catch (IllegalArgumentException e) {
      throw new IdParseFailedException("Failed to parse value as CRID. value=[" + value + "]", e);
    }
  }

  @Override
  public Crid from(UUID value) {
    final long msb = value.getMostSignificantBits();
    final long lsb = value.getLeastSignificantBits();
    final byte[] bytes = ByteArrayUtil.longToBytes(msb, lsb);
    return new Crid(
        ByteArrayUtil.sixBytesToEpochMilli(bytes), Arrays.copyOfRange(bytes, 6, bytes.length));
  }
}
