package pw.itr0.selidor.identifier.codec;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.security.SecureRandom;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class LowerCrockfordBase32SlowTest {

  @Test
  @Tag("slow")
  void testRandomBytes() {
    assertSoftly(
        s -> {
          final SecureRandom random = new SecureRandom();
          for (int i = 0; i < 1000; i++) {
            final byte[] r = new byte[16];
            random.nextBytes(r);

            // byte[] <-> byte[]
            final byte[] bytes = LowerCrockfordBase32.encodeToBytes(r);
            s.assertThat(bytes).hasSize(26);
            s.assertThat(LowerCrockfordBase32.decode(bytes)).isEqualTo(r);

            // byte[] <-> String
            final String string = LowerCrockfordBase32.encode(r);
            s.assertThat(string).hasSize(26);
            s.assertThat(LowerCrockfordBase32.decode(string)).isEqualTo(r);
          }
        });
  }
}
