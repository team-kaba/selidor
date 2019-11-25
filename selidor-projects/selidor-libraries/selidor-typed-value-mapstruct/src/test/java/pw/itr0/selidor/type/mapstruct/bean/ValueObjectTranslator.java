package pw.itr0.selidor.type.mapstruct.bean;

import pw.itr0.selidor.type.mapstruct.bean.another.AnotherSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

class ValueObjectTranslator {
  Money copy(Money s) {
    return s;
  }

  Money map(OneSomeValue s) {
    return s.getValue();
  }

  OneSomeValue mapToOne(Money s) {
    return new OneSomeValue(s);
  }

  OneSomeValue copy(OneSomeValue s) {
    return s;
  }

  Money map(AnotherSomeValue s) {
    if (s == null) {
      return null;
    }
    return s.getValue();
  }

  AnotherSomeValue mapToAnother(Money s) {
    return new AnotherSomeValue(s);
  }

  AnotherSomeValue copy(AnotherSomeValue s) {
    return s;
  }

  AnotherSomeValue mapToAnother(OneSomeValue s) {
    if (s == null) {
      return null;
    }
    return new AnotherSomeValue(s.getValue());
  }

  OneSomeValue mapToOne(AnotherSomeValue s) {
    if (s == null) {
      return null;
    }
    return new OneSomeValue(s.getValue());
  }
}
