package pw.itr0.selidor.identifier;

public interface IdGenerator<T extends Id> {

  T next();

  T parse(String value) throws IdParseFailedException;
}
