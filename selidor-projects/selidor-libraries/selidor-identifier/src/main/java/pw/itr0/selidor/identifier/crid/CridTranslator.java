package pw.itr0.selidor.identifier.crid;

import java.util.UUID;

/** {@link Crid} と基本クラスの間の変換を行うクラスです。 */
@SuppressWarnings("WeakerAccess") // utility class
public class CridTranslator {

  private final CridGenerator id;

  public CridTranslator(CridGenerator id) {
    this.id = id;
  }

  public Crid map(String id) {
    if (id == null) {
      return null;
    }
    return this.id.parse(id);
  }

  public Crid map(UUID id) {
    if (id == null) {
      return null;
    }
    return this.id.from(id);
  }

  public String mapToString(Crid id) {
    if (id == null) {
      return null;
    }
    return id.toString();
  }

  public UUID mapToUuid(Crid id) {
    if (id == null) {
      return null;
    }
    return id.uuid();
  }

  public byte[] mapToBytes(Crid id) {
    if (id == null) {
      return null;
    }
    return id.bytes();
  }
}
