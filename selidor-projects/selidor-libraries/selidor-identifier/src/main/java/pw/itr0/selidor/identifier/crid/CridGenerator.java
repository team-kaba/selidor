package pw.itr0.selidor.identifier.crid;

import java.time.Clock;
import java.util.Random;
import java.util.UUID;
import pw.itr0.selidor.identifier.Id128Generator;
import pw.itr0.selidor.identifier.IdParseFailedException;

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
    return Crid.parse(value);
  }

  @Override
  public Crid from(UUID value) {
    return Crid.from(value);
  }
}
