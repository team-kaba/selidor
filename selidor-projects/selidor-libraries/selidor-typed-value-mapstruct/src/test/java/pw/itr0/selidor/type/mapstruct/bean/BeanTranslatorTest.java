package pw.itr0.selidor.type.mapstruct.bean;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;

class BeanTranslatorTest {

  private static final BeanTranslator sut = Mappers.getMapper(BeanTranslator.class);

  @ParameterizedTest
  @MethodSource("formAndEntity")
  @DisplayName("FormからEntityへの変換 (raw -> typed)")
  void testTranslateFormToEntity(FormOne form, EntityOne entity) {
    final EntityOne actual = sut.translateToEntity(form);
    assertThat(actual).isEqualTo(entity);
  }

  @ParameterizedTest
  @MethodSource("formAndEntity")
  @DisplayName("EntityからFormへの変換 (typed -> raw)")
  void testTranslateEntityToForm(FormOne form, EntityOne entity) {
    final FormOne actual = sut.translateToForm(entity);
    assertThat(actual).isEqualTo(form);
  }

  @ParameterizedTest
  @MethodSource("entity")
  @DisplayName("Entityから同じEntityへの変換 (typed -> typed (same)")
  void testTranslateEntityToSameEntity(EntityOne entity) {
    final EntityOne copy = sut.copy(entity);
    assertThat(copy).isEqualTo(entity);
    if (entity != null) {
      assertThat(copy).isNotSameAs(entity);
    }
  }

  @ParameterizedTest
  @MethodSource("entityOneAndAnother")
  @DisplayName("Entityから別のEntityへの変換 (typed -> typed (another))")
  void testTranslateEntityToAntherEntity(EntityOne one, EntityAnother another) {
    {
      final EntityAnother actual = sut.translateToAnother(one);
      assertThat(actual).isEqualTo(another);
    }
    {
      final EntityOne actual = sut.translateToOne(another);
      assertThat(actual).isEqualTo(one);
    }
  }

  @ParameterizedTest
  @MethodSource("form")
  @DisplayName("Formから同じFormへの変換 (raw -> raw (same))")
  void testTranslateFormToSameForm(FormOne form) {
    final FormOne copy = sut.copy(form);
    assertThat(copy).isEqualTo(form);
    if (form != null) {
      assertThat(copy).isNotSameAs(form);
    }
  }

  @ParameterizedTest
  @MethodSource("formOneAndAnother")
  @DisplayName("Formから別のFormへの変換 (raw -> raw (another))")
  void testTranslateFormToAntherForm(FormOne one, FormAnother another) {
    {
      final FormAnother actual = sut.translateToAnother(one);
      assertThat(actual).isEqualTo(another);
    }
    {
      final FormOne actual = sut.translateToOne(another);
      assertThat(actual).isEqualTo(one);
    }
  }

  static Iterable<Arguments> formAndEntity() {
    return List.of(
        arguments(null, null),
        arguments(new FormOne(), new EntityOne()),
        arguments(FormOne.nonNullValues(), EntityOne.nonNullValues()));
  }

  static Iterable<Arguments> entity() {
    return List.of(
        arguments((Object) null), arguments(new EntityOne()), arguments(EntityOne.nonNullValues()));
  }

  static Iterable<Arguments> entityOneAndAnother() {
    return List.of(
        arguments(null, null),
        arguments(new EntityOne(), new EntityAnother()),
        arguments(EntityOne.nonNullValues(), EntityAnother.nonNullValues()));
  }

  static Iterable<Arguments> form() {
    return List.of(
        arguments((Object) null), arguments(new FormOne()), arguments(FormOne.nonNullValues()));
  }

  static Iterable<Arguments> formOneAndAnother() {
    return List.of(
        arguments(null, null),
        arguments(new FormOne(), new FormAnother()),
        arguments(FormOne.nonNullValues(), FormAnother.nonNullValues()));
  }
}
