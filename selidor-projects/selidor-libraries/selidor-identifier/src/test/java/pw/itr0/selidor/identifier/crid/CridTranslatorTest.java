package pw.itr0.selidor.identifier.crid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.security.SecureRandom;
import java.time.Clock;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class CridTranslatorTest {

  private static final CridGenerator generator =
      new CridGenerator(Clock.systemDefaultZone(), new SecureRandom());
  private static final CridTranslator sut = new CridTranslator(generator);

  @ParameterizedTest
  @MethodSource("cridString")
  @DisplayName("String")
  void mapString(Crid crid, String string) {
    assertThat(sut.mapToString(crid)).isEqualTo(string);
    assertThat(sut.map(string)).isEqualTo(crid);
  }

  @ParameterizedTest
  @MethodSource("cridUuid")
  @DisplayName("UUID")
  void mapUuid(Crid crid, UUID uuid) {
    assertThat(sut.mapToUuid(crid)).isEqualTo(uuid);
    assertThat(sut.map(uuid)).isEqualTo(crid);
  }

  @ParameterizedTest
  @MethodSource("cridBytes")
  @DisplayName("bytes")
  void mapUuid(Crid crid, byte[] bytes) {
    assertThat(sut.mapToBytes(crid)).isEqualTo(bytes);
  }

  private static Iterable<Arguments> cridString() {
    final Crid id = generator.next();
    return List.of(arguments(null, null), arguments(id, id.toString()));
  }

  private static Iterable<Arguments> cridUuid() {
    final Crid id = generator.next();
    return List.of(arguments(null, null), arguments(id, id.uuid()));
  }

  private static Iterable<Arguments> cridBytes() {
    final Crid id = generator.next();
    return List.of(arguments(null, null), arguments(id, id.bytes()));
  }
}
