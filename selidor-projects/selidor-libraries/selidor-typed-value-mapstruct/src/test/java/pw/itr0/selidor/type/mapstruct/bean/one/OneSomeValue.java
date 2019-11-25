package pw.itr0.selidor.type.mapstruct.bean.one;

import lombok.Value;
import pw.itr0.selidor.type.TypedValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

public class OneSomeValue extends TypedValue<Money> {

  public OneSomeValue(Money value) {
    super(value);
  }

  @Value
  public static class Money {
    private int money;
  }
}
