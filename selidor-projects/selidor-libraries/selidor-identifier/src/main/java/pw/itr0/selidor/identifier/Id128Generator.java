package pw.itr0.selidor.identifier;

import java.util.UUID;

public interface Id128Generator<T extends Id128> extends IdGenerator<T> {
  T from(UUID value);
}
