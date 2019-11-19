package pw.itr0.selidor.identifier;

import java.io.Serializable;

public interface Id extends Serializable {
  String toString();

  byte[] bytes();
}
