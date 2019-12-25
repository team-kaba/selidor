package pw.itr0.selidor.type.test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.identifier.crid.CridGenerator;
import pw.itr0.selidor.type.TypedCrid;

@ExtendWith(SoftAssertionsExtension.class)
class TypedCridTest {

  @Test
  void equality(SoftAssertions s) {
    final UUID id = UUID.randomUUID();
    final DocumentId document = new DocumentId(Crid.from(id));
    s.assertThat(document).isEqualTo(new DocumentId(Crid.from(id)));
    s.assertThat(document).isNotEqualTo(new IndexId(Crid.from(id)));
  }

  @Test
  void timestamp(SoftAssertions s) {
    final Instant instant = Instant.now();
    final CridGenerator id =
        new CridGenerator(Clock.fixed(instant, ZoneId.systemDefault()), new Random());
    final DocumentId document = new DocumentId(id.next());
    s.assertThat(document.timestamp()).map(Instant::toEpochMilli).get().isEqualTo(instant.toEpochMilli());
  }

  @Test
  void random(SoftAssertions s) {
    final Random random = new Random(1);
    final CridGenerator id = new CridGenerator(Clock.systemDefaultZone(), random);
    final DocumentId document = new DocumentId(id.next());
    final Random newRandom = new Random(1);
    byte[] expected = new byte[10];
    newRandom.nextBytes(expected);
    s.assertThat(document.randomness()).get().isEqualTo(expected);
  }

  @Test
  void uuid(SoftAssertions s) {
    final UUID id = UUID.randomUUID();
    s.assertThat(new DocumentId(Crid.from(id)).uuid()).isEqualTo(new IndexId(Crid.from(id)).uuid());
  }

  private static final class DocumentId extends TypedCrid<DocumentId> {
    private DocumentId(Crid value) {
      super(value, false);
    }
  }

  private static final class IndexId extends TypedCrid<IndexId> {
    private IndexId(Crid value) {
      super(value, true);
    }
  }
}
