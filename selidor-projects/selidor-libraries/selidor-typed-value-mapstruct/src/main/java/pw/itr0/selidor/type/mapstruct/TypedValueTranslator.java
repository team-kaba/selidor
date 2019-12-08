package pw.itr0.selidor.type.mapstruct;

import java.util.UUID;
import pw.itr0.selidor.type.TypedValue;
import pw.itr0.selidor.type.mapstruct.util.TypedBooleanTranslator;
import pw.itr0.selidor.type.mapstruct.util.TypedCridTranslator;
import pw.itr0.selidor.type.mapstruct.util.TypedLongIdTranslator;
import pw.itr0.selidor.type.mapstruct.util.TypedStringTranslator;

/** {@link pw.itr0.selidor.type} に配置されている {@link TypedValue} の抽象クラスと基本クラスの間の変換を行うクラスです。 */
public class TypedValueTranslator
    implements TypedBooleanTranslator,
        TypedStringTranslator,
        TypedCridTranslator,
        TypedLongIdTranslator {

  ////////////////////////////////////////////////////////////////////////////////////
  // Basic types
  ////////////////////////////////////////////////////////////////////////////////////

  public UUID mapStringToUuid(String source) {
    return source != null ? UUID.fromString(source) : null;
  }

  public String mapUuidToString(UUID source) {
    return source != null ? source.toString() : null;
  }
}
