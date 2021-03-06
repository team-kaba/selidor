package pw.itr0.selidor.type.mapstruct;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.identifier.crid.CridGenerator;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.identifier.random.RandomLongIdGenerator;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherCrid;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherLongId;
import pw.itr0.selidor.type.mapstruct.bean.another.AnotherStringValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneBooleanValue;
import pw.itr0.selidor.type.mapstruct.bean.one.OneCrid;
import pw.itr0.selidor.type.mapstruct.bean.one.OneLongId;
import pw.itr0.selidor.type.mapstruct.bean.one.OneStringValue;

@ExtendWith(SoftAssertionsExtension.class)
class TypedValueTranslatorTest {

  private static final TypedValueTranslator sut = new TypedValueTranslator();

  @Nested
  class TypedBooleanTranslatorTest {
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    @DisplayName("Boolean - not null")
    void mapBoolean(boolean source, SoftAssertions s) {
      final OneBooleanValue typed = sut.mapBooleanToTypedBoolean(source, OneBooleanValue.class);
      s.assertThat(typed.getValue()).get().isEqualTo(source);
      s.assertThat(typed.getNullableValue()).isEqualTo(source);

      final boolean raw = sut.mapTypedBooleanToBoolean(typed);
      s.assertThat(raw).isEqualTo(source);

      final AnotherBooleanValue copied =
          sut.mapBetweenTypedBoolean(typed, AnotherBooleanValue.class);
      s.assertThat(copied.isTrue()).isEqualTo(typed.isTrue());
    }

    @Test
    @DisplayName("Boolean - null")
    void mapBooleanNullValue(SoftAssertions s) {
      final OneBooleanValue typed = sut.mapBooleanToTypedBoolean(null, OneBooleanValue.class);
      s.assertThat(typed.getValue()).isEmpty();
      s.assertThat(typed.getNullableValue()).isNull();
      s.assertThat(typed.isNull()).isTrue();

      final Boolean raw = sut.mapTypedBooleanToBoolean(typed);
      s.assertThat(raw).isNull();

      final AnotherBooleanValue copied =
          sut.mapBetweenTypedBoolean(null, AnotherBooleanValue.class);
      s.assertThat(copied.getValue()).isEmpty();
      s.assertThat(copied.isNull()).isTrue();
    }
  }

  @Nested
  class TypedStringTranslatorTest {
    @Test
    @DisplayName("String - not null")
    void mapString(SoftAssertions s) {
      final String source = "raw string value";
      final OneStringValue typed = sut.mapStringToTypedString(source, OneStringValue.class);
      s.assertThat(typed.getValue()).get().isEqualTo(source);
      s.assertThat(typed.getNullableValue()).isEqualTo(source);

      final String raw = sut.mapTypedStringToString(typed);
      s.assertThat(raw).isEqualTo(source);

      final AnotherStringValue copied = sut.mapBetweenTypedString(typed, AnotherStringValue.class);
      s.assertThat(copied.getValue()).get().isEqualTo(source);
    }

    @Test
    @DisplayName("String - null")
    @SuppressWarnings("ConstantConditions")
    void mapStringNullValue(SoftAssertions s) {
      final String source = null;
      final OneStringValue typed = sut.mapStringToTypedString(source, OneStringValue.class);
      s.assertThat(typed.isNull()).isTrue();

      final String raw = sut.mapTypedStringToString(typed);
      s.assertThat(raw).isNull();

      final AnotherStringValue copied = sut.mapBetweenTypedString(typed, AnotherStringValue.class);
      s.assertThat(copied.isNull()).isTrue();
    }
  }

  @Nested
  class TypedCridTranslatorTest {

    private final CridGenerator generator =
        new CridGenerator(Clock.systemDefaultZone(), new SecureRandom());

    @Test
    @DisplayName("Crid - not null")
    void mapCrid(SoftAssertions s) {
      final Crid source = generator.next();
      final OneCrid typed = sut.mapCridToTypedCrid(source, OneCrid.class);
      s.assertThat(typed.getValue()).get().isEqualTo(source);
      s.assertThat(typed.getNullableValue()).isEqualTo(source);

      final Crid raw = sut.mapTypedCridToCrid(typed);
      s.assertThat(raw).isEqualTo(source);

      final AnotherCrid copied = sut.mapBetweenTypedCrid(typed, AnotherCrid.class);
      s.assertThat(copied.getValue()).get().isEqualTo(source);
    }

    @Test
    @DisplayName("Crid - null")
    void mapCridNullValue(SoftAssertions s) {
      {
        final OneCrid typed = sut.mapCridToTypedCrid(null, OneCrid.class);
        s.assertThat(typed.isNull()).isTrue();

        final Crid raw = sut.mapTypedCridToCrid(null);
        s.assertThat(raw).isNull();

        final AnotherCrid copied = sut.mapBetweenTypedCrid(null, AnotherCrid.class);
        s.assertThat(copied.isNull()).isTrue();
      }
      {
        final OneCrid typed = new OneCrid(null);

        final Crid raw = sut.mapTypedCridToCrid(typed);
        s.assertThat(raw).isNull();

        final AnotherCrid copied = sut.mapBetweenTypedCrid(typed, AnotherCrid.class);
        s.assertThat(copied.isNull()).isTrue();
      }
    }

    @Test
    @DisplayName("String - not null")
    void mapString(SoftAssertions s) {
      final Crid crid = generator.next();
      final String source = crid.toString();
      final OneCrid typed = sut.mapStringToTypedCrid(source, OneCrid.class);
      s.assertThat(typed.getValue()).get().isEqualTo(crid);
      s.assertThat(typed.getNullableValue()).isEqualTo(crid);

      final String raw = sut.mapTypedCridToString(typed);
      s.assertThat(raw).isEqualTo(source);
    }

    @Test
    @DisplayName("String - null")
    void mapStringNullValue(SoftAssertions s) {
      {
        final OneCrid typed = sut.mapStringToTypedCrid(null, OneCrid.class);
        s.assertThat(typed.isNull()).isTrue();

        final String raw = sut.mapTypedCridToString(null);
        s.assertThat(raw).isNull();
      }
      {
        final OneCrid typed = new OneCrid(null);

        final String raw = sut.mapTypedCridToString(typed);
        s.assertThat(raw).isNull();
      }
    }

    @Test
    @DisplayName("UUID - not null")
    void mapUuid(SoftAssertions s) {
      final Crid crid = generator.next();
      final UUID source = crid.uuid();
      final OneCrid typed = sut.mapUuidToTypedCrid(source, OneCrid.class);
      s.assertThat(typed.getValue()).get().isEqualTo(crid);
      s.assertThat(typed.getNullableValue()).isEqualTo(crid);

      final UUID raw = sut.mapTypedCridToUuid(typed);
      s.assertThat(raw).isEqualTo(source);

      final String string = sut.mapUuidToString(raw);
      s.assertThat(string).isEqualTo(raw.toString());
      final UUID uuid = sut.mapStringToUuid(string);
      s.assertThat(uuid).isEqualTo(source);
    }

    @Test
    @DisplayName("UUID - null")
    void mapUuidNullValue(SoftAssertions s) {
      {
        final OneCrid typed = sut.mapUuidToTypedCrid(null, OneCrid.class);
        s.assertThat(typed.isNull()).isTrue();

        final UUID raw = sut.mapTypedCridToUuid(null);
        s.assertThat(raw).isNull();

        final String string = sut.mapUuidToString(null);
        s.assertThat(string).isNull();
        final UUID uuid = sut.mapStringToUuid(null);
        s.assertThat(uuid).isNull();
      }
      {
        final OneCrid typed = new OneCrid(null);

        final UUID raw = sut.mapTypedCridToUuid(typed);
        s.assertThat(raw).isNull();
      }
    }

    @Test
    @DisplayName("UUID <-> Crid String")
    void mapUuidAndCridStringNullValue(SoftAssertions s) {
      final Crid id = generator.next();

      s.assertThat(sut.mapUuidToCridString(id.uuid())).isEqualTo(id.toString());
      s.assertThat(sut.mapCridStringToUuid(id.toString())).isEqualTo(id.uuid());

      final UUID raw = sut.mapCridStringToUuid(null);
      s.assertThat(raw).isNull();
      final String string = sut.mapUuidToCridString(null);
      s.assertThat(string).isNull();
    }
  }

  @Nested
  class TypedLongIdTranslatorTest {

    private final RandomLongIdGenerator generator = new RandomLongIdGenerator();

    @Test
    @DisplayName("LongId - not null")
    void mapLongId(SoftAssertions s) {
      final LongId source = generator.next();
      final OneLongId typed = sut.mapLongIdToTypedLongId(source, OneLongId.class);
      s.assertThat(typed.getValue()).get().isEqualTo(source);

      final LongId raw = sut.mapTypedLongIdToLongId(typed);
      s.assertThat(raw).isEqualTo(source);

      final AnotherLongId copied = sut.mapBetweenTypedLongId(typed, AnotherLongId.class);
      s.assertThat(copied.getValue()).get().isEqualTo(source);
    }

    @Test
    @DisplayName("LongId - null")
    void mapLongIdNullValue(SoftAssertions s) {
      final OneLongId typed = sut.mapLongIdToTypedLongId(null, OneLongId.class);
      s.assertThat(typed).isNotNull();
      s.assertThat(typed.getNullableValue()).isNull();
      s.assertThat(typed.isNull()).isTrue();

      final LongId raw = sut.mapTypedLongIdToLongId(null);
      s.assertThat(raw).isNull();

      final AnotherLongId copied = sut.mapBetweenTypedLongId(null, AnotherLongId.class);
      s.assertThat(copied).isNotNull();
      s.assertThat(copied.isNull()).isTrue();
    }

    @Test
    @DisplayName("String - not null")
    void mapString(SoftAssertions s) {
      final LongId id = generator.next();
      final String source = id.toString();
      final OneLongId typed = sut.mapStringToTypedLongId(source, OneLongId.class);
      s.assertThat(typed.getValue()).get().isEqualTo(id);
      s.assertThat(typed.getNullableValue()).isEqualTo(id);

      final String raw = sut.mapTypedLongIdToString(typed);
      s.assertThat(raw).isEqualTo(source);
    }

    @Test
    @DisplayName("String - null")
    void mapStringNullValue(SoftAssertions s) {
      final OneLongId typed = sut.mapStringToTypedLongId(null, OneLongId.class);
      s.assertThat(typed).isNotNull();
      s.assertThat(typed.getValue()).isEmpty();
      s.assertThat(typed.getNullableValue()).isNull();
      s.assertThat(typed.isNull()).isTrue();

      final String raw = sut.mapTypedLongIdToString(null);
      s.assertThat(raw).isNull();

      final AnotherLongId copied = sut.mapBetweenTypedLongId(null, AnotherLongId.class);
      s.assertThat(copied).isNotNull();
      s.assertThat(copied.isNull()).isTrue();
    }

    @Test
    @DisplayName("Long - not null")
    void mapUuid(SoftAssertions s) {
      final LongId id = generator.next();
      final Long source = id.longValue();
      final OneLongId typed = sut.mapLongToTypedLongId(source, OneLongId.class);
      s.assertThat(typed.getValue()).get().isEqualTo(id);
      s.assertThat(typed.getNullableValue()).isEqualTo(id);

      final Long raw = sut.mapTypedLongIdToLong(typed);
      s.assertThat(raw).isEqualTo(source);
    }

    @Test
    @DisplayName("Long - null")
    void mapUuidNullValue(SoftAssertions s) {
      final OneLongId typed = sut.mapLongToTypedLongId(null, OneLongId.class);
      s.assertThat(typed).isNotNull();
      s.assertThat(typed.getValue()).isEmpty();
      s.assertThat(typed.getNullableValue()).isNull();
      s.assertThat(typed.isNull()).isTrue();

      final Long raw = sut.mapTypedLongIdToLong(null);
      s.assertThat(raw).isNull();
    }
  }
}
