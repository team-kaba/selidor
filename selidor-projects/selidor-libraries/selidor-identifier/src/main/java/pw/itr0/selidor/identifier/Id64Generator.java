package pw.itr0.selidor.identifier;

public interface Id64Generator<T extends Id64> extends IdGenerator<T> {
  T from(long value);
}
