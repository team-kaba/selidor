package pw.itr0.selidor.type.mapstruct.bean;

import java.time.ZoneOffset;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherComparableValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneComparableValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneSomeValue.Money;

class ValueObjectTranslator {
  Money copy(Money s) {
    return s;
  }

  Money map(OneSomeValue s) {
    if (s == null) {
      return null;
    }
    return s.getNullableValue();
  }

  OneSomeValue mapToOne(Money s) {
    if (s == null) {
      return null;
    }
    return new OneSomeValue(s);
  }

  OneSomeValue copy(OneSomeValue s) {
    return s;
  }

  Money map(AnotherSomeValue s) {
    if (s == null) {
      return null;
    }
    return s.getNullableValue();
  }

  AnotherSomeValue mapToAnother(Money s) {
    if (s == null) {
      return null;
    }
    return new AnotherSomeValue(s);
  }

  AnotherSomeValue copy(AnotherSomeValue s) {
    return s;
  }

  AnotherSomeValue mapToAnother(OneSomeValue s) {
    if (s == null) {
      return null;
    }
    return new AnotherSomeValue(s.getNullableValue());
  }

  OneSomeValue mapToOne(AnotherSomeValue s) {
    if (s == null) {
      return null;
    }
    return new OneSomeValue(s.getNullableValue());
  }

  OneComparableValue mapToOne(ZoneOffset s) {
    if (s == null) {
      return null;
    }
    return new OneComparableValue(s);
  }

  ZoneOffset mapToOne(OneComparableValue s) {
    if (s == null) {
      return null;
    }
    return s.getNullableValue();
  }

  OneComparableValue copy(OneComparableValue s) {
    return s;
  }

  AnotherComparableValue mapToAnother(ZoneOffset s) {
    if (s == null) {
      return null;
    }
    return new AnotherComparableValue(s);
  }

  ZoneOffset mapToAnother(AnotherComparableValue s) {
    if (s == null) {
      return null;
    }
    return s.getNullableValue();
  }

  AnotherComparableValue copy(AnotherComparableValue s) {
    return s;
  }
}
