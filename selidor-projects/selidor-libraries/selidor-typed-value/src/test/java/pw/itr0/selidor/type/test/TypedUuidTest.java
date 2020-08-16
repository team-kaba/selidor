package pw.itr0.selidor.type.test;

import java.util.UUID;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.type.NullValueUnboxingException;
import pw.itr0.selidor.type.TypedUuid;

@ExtendWith(SoftAssertionsExtension.class)
class TypedUuidTest {

  @Test
  void equality(SoftAssertions s) {
    final UUID id = UUID.randomUUID();
    final DocumentId document = new DocumentId(id);
    s.assertThat(document)
        .isEqualTo(new DocumentId(id))
        .isNotEqualTo(new IndexId(id))
        .isNotEqualTo(null);
  }

  @Test
  void getLeastSignificantBits(SoftAssertions s) {
    final UUID id = UUID.randomUUID();
    final DocumentId document = new DocumentId(id);
    s.assertThat(document.getLeastSignificantBits()).isEqualTo(id.getLeastSignificantBits());
    s.assertThatThrownBy(() -> new DocumentId(null).getLeastSignificantBits())
        .isInstanceOf(NullValueUnboxingException.class)
        .hasMessageContaining(DocumentId.class.getSimpleName());
  }

  @Test
  void getMostSignificantBits(SoftAssertions s) {
    final UUID id = UUID.randomUUID();
    final DocumentId document = new DocumentId(id);
    s.assertThat(document.getMostSignificantBits()).isEqualTo(id.getMostSignificantBits());
    s.assertThatThrownBy(() -> new DocumentId(null).getMostSignificantBits())
        .isInstanceOf(NullValueUnboxingException.class)
        .hasMessageContaining(DocumentId.class.getSimpleName());
  }

  @Test
  void version(SoftAssertions s) {
    final UUID id = UUID.randomUUID();
    final DocumentId document = new DocumentId(id);
    s.assertThat(document.version()).isEqualTo(id.version());
    s.assertThatThrownBy(() -> new DocumentId(null).version())
        .isInstanceOf(NullValueUnboxingException.class)
        .hasMessageContaining(DocumentId.class.getSimpleName());
  }

  @Test
  void variant(SoftAssertions s) {
    final UUID id = UUID.randomUUID();
    final DocumentId document = new DocumentId(id);
    s.assertThat(document.variant()).isEqualTo(id.variant());
    s.assertThatThrownBy(() -> new DocumentId(null).variant())
        .isInstanceOf(NullValueUnboxingException.class)
        .hasMessageContaining(DocumentId.class.getSimpleName());
  }

  private static final class DocumentId extends TypedUuid<DocumentId> {
    private DocumentId(UUID value) {
      super(value, false);
    }
  }

  private static final class IndexId extends TypedUuid<IndexId> {
    private IndexId(UUID value) {
      super(value, true);
    }
  }
}
