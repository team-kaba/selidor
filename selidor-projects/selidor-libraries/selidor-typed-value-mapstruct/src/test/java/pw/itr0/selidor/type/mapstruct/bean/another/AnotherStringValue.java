package pw.itr0.selidor.type.mapstruct.bean.another;

import pw.itr0.selidor.type.TypedString;

public final class AnotherStringValue extends TypedString<AnotherStringValue> {
  public AnotherStringValue(String value) {
    super(value, true);
  }
}
