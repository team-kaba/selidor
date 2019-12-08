package pw.itr0.selidor.identifier.crid;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.security.SecureRandom;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pw.itr0.selidor.identifier.IdParseFailedException;
import pw.itr0.selidor.util.ByteArrayUtil;

class CridGeneratorTest {

  @Test
  @DisplayName("指定されたClockとRandomで、CRIDが生成されること。")
  void testGenerateWithSpecifiedClockAndRandom() {
    final Instant now = Instant.now();
    final Clock clock = Clock.fixed(now, ZoneId.systemDefault());
    final Random random = new Random(1);
    final CridGenerator sut = new CridGenerator(clock, random);

    final Crid actual = sut.next();
    final Random random2 = new Random(1);
    final byte[] expectedRandom = new byte[10];
    random2.nextBytes(expectedRandom);
    assertThat(actual).isEqualTo(new Crid(now.toEpochMilli(), expectedRandom));
  }

  @Test
  @DisplayName("文字列をパースして、CRIDにできること。")
  void testParseCridString() {
    final CridGenerator sut = new CridGenerator(Clock.systemDefaultZone(), new SecureRandom());

    final Crid crid = sut.next();
    assertThat(sut.parse(crid.toString())).isEqualTo(crid);
  }

  @Test
  @DisplayName("文字列のパースに失敗した場合は、IdParseFailedExceptionが発生すること")
  void testParseCridStringFailure() {
    final CridGenerator sut = new CridGenerator(Clock.systemDefaultZone(), new SecureRandom());

    assertThatExceptionOfType(IdParseFailedException.class)
        .isThrownBy(() -> sut.parse("some invalid string"))
        .withMessageContaining("value=[some invalid string]");
  }

  @Test
  @DisplayName("UUIDからCRIDに変換できること。")
  void testFromUuid() {
    final CridGenerator sut = new CridGenerator(Clock.systemDefaultZone(), new SecureRandom());

    final Crid crid = sut.next();
    final UUID uuid =
        new UUID(
            ByteArrayUtil.bytesToLong(crid.bytes(), 0), ByteArrayUtil.bytesToLong(crid.bytes(), 8));
    assertThat(sut.from(uuid)).isEqualTo(crid);
  }
}
