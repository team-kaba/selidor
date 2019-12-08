package pw.itr0.selidor.identifier.random;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import pw.itr0.selidor.identifier.Id64Generator;
import pw.itr0.selidor.identifier.IdParseFailedException;

public class RandomLongIdGenerator implements Id64Generator<LongId> {

  private final Random random;

  public RandomLongIdGenerator(Random random) {
    this.random = random;
  }

  public RandomLongIdGenerator() {
    Random random;
    try {
      random = SecureRandom.getInstance("NativePRNGNonBlocking");
    } catch (NoSuchAlgorithmException e) {
      try {
        random = SecureRandom.getInstanceStrong();
      } catch (NoSuchAlgorithmException ex) {
        random = new SecureRandom();
      }
    }
    this.random = random;
  }

  @Override
  public LongId next() {
    return new LongId(random.nextLong());
  }

  @Override
  public LongId parse(String value) throws IdParseFailedException {
    return LongId.parse(value);
  }

  @Override
  public LongId from(long value) {
    return new LongId(value);
  }
}
