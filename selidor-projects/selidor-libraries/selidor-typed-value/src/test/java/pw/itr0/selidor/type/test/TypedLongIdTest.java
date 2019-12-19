package pw.itr0.selidor.type.test;

import java.util.Random;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.type.TypedLongId;

@ExtendWith(SoftAssertionsExtension.class)
class TypedLongIdTest {
  @Test
  void equality(SoftAssertions s) {
    final long id = new Random().nextLong();
    final DocumentId document = new DocumentId(LongId.from(id));
    s.assertThat(document).isEqualTo(new DocumentId(LongId.from(id)));
    s.assertThat(document).isNotEqualTo(new IndexId(LongId.from(id)));
  }

  @Test
  void longValue(SoftAssertions s) {
    final long id = new Random().nextLong();
    s.assertThat(new DocumentId(LongId.from(id)).longValue())
        .isEqualTo(new IndexId(LongId.from(id)).longValue());
  }

  private static final class DocumentId extends TypedLongId<DocumentId> {
    private DocumentId(LongId value) {
      super(value, true);
    }
  }

  private static final class IndexId extends TypedLongId<IndexId> {
    private IndexId(LongId value) {
      super(value, false);
    }
  }
}
