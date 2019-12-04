package pw.itr0.selidor.identifier.random;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.SecureRandom;
import java.security.SecureRandomSpi;
import java.security.Security;
import java.util.Arrays;
import java.util.Random;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import pw.itr0.selidor.identifier.IdParseFailedException;

@ExtendWith(SoftAssertionsExtension.class)
class RandomLongIdGeneratorTest {

  private static final long LONG = new Random().nextLong();

  private static final Random RANDOM =
      new Random() {
        @Override
        public long nextLong() {
          return LONG;
        }
      };

  @Test
  void testConstructors(SoftAssertions s) {
    {
      final RandomLongIdGenerator id = new RandomLongIdGenerator();
      s.assertThat(id.next()).isNotNull();
    }
    {
      final RandomLongIdGenerator id = new RandomLongIdGenerator(RANDOM);
      s.assertThat(id.next().longValue()).isEqualTo(LONG);
    }
  }

  @Test
  void testParseLongString(SoftAssertions s) {
    final String longString = "1234567890123456789";
    s.assertThat(new RandomLongIdGenerator().parse(longString))
        .isEqualTo(new LongId(1234567890123456789L));
  }

  @Test
  void testParseLongStringFailure() {
    assertThatExceptionOfType(IdParseFailedException.class)
        .isThrownBy(() -> new RandomLongIdGenerator().parse("some invalid string"))
        .withMessageContaining("value=[some invalid string]");
  }

  @Test
  void testFromLong(SoftAssertions s) {
    final long l = 1234567890;
    s.assertThat(new RandomLongIdGenerator().from(l)).isEqualTo(new LongId(l));
  }

  @Test
  // FIXME: マルチスレッドで実行したら死んでしまうかもしれない。
  void testNativePRNGNonBlockingNotExists(SoftAssertions s) throws NoSuchAlgorithmException {
    final Provider[] providers = Security.getProviders("SecureRandom.NativePRNGNonBlocking");
    final String strongAlgorithmsKey = "securerandom.strongAlgorithms";
    final String strongAlgorithms = Security.getProperty(strongAlgorithmsKey);
    final Class<?> originalNonBlockingClass =
        SecureRandom.getInstance("NativePRNGNonBlocking").getProvider().getClass();
    final Class<?> originalStrongClass = SecureRandom.getInstanceStrong().getProvider().getClass();
    final Class<?> originalSha1Class =
        SecureRandom.getInstance("SHA1PRNG").getProvider().getClass();
    try {
      // NativePRNGNonBlocking で取得できないようにする。
      for (Provider provider : providers) {
        Security.removeProvider(provider.getName());
      }
      // デフォルトの疑似乱数生成アルゴリズムとして、ダミーのを登録しておく。
      Security.insertProviderAt(new FakeSha1PrngProvider(), 1);
      s.assertThatThrownBy(() -> SecureRandom.getInstance("NativePRNGNonBlocking"))
          .as("NativePRNGNonBlockingのAlgorithmでSecureRandomが取得できなくなっていること")
          .isInstanceOf(NoSuchAlgorithmException.class);
      s.assertThat(new RandomLongIdGenerator().next())
          .as("NativePRNGNonBlockingのAlgorithmでSecureRandomが取得できなくてもエラーにならずにLongIdを生成できること。")
          .isNotNull();
      // さらに、Security.getInstanceStrong() で取得できないようにする。
      Security.setProperty(strongAlgorithmsKey, "");
      s.assertThatThrownBy(SecureRandom::getInstanceStrong)
          .as("SecureRandom.getInstanceStrongでSecureRandomが取得できなくなっていること")
          .isInstanceOf(NoSuchAlgorithmException.class);
      s.assertThat(new RandomLongIdGenerator().next())
          .as("SecureRandom.getInstanceStrongでSecureRandomが取得できなくてもエラーにならずにLongIdを生成できること。")
          .isNotNull();
    } finally {
      // 元に戻す
      Arrays.stream(providers).forEachOrdered(Security::addProvider);
      Security.removeProvider(FakeSha1PrngProvider.PROVIDER_NAME);
      Security.setProperty(strongAlgorithmsKey, strongAlgorithms);
    }
    // いちおう、Providerが戻ってることを確認しておく。
    s.assertThat(SecureRandom.getInstance("SHA1PRNG").getProvider().getClass())
        .isEqualTo(originalSha1Class);
    s.assertThat(SecureRandom.getInstance("NativePRNGNonBlocking").getProvider().getClass())
        .isEqualTo(originalNonBlockingClass);
    s.assertThat(SecureRandom.getInstanceStrong().getProvider().getClass())
        .isEqualTo(originalStrongClass);
  }

  private static final class FakeSha1PrngProvider extends Provider {

    private static final String PROVIDER_NAME = "FakeSha1PrngProvider";

    private FakeSha1PrngProvider() {
      super(PROVIDER_NAME, "1.0", null);
      put("SecureRandom.SHA1PRNG", FakeSha1PrngSecureRandomSpi.class.getName());
    }
  }

  // SecureRandomSpiの実装クラスとそのコンストラクタはpublicじゃないと実行時にエラーになる。
  // Private class:
  // > java.security.NoSuchAlgorithmException: class configured for SecureRandom (provider:
  // >    FakeSha1PrngProvider) is not public.
  // Private constructor:
  // > java.security.NoSuchAlgorithmException: Error constructing implementation (algorithm:
  // >   SHA1PRNG, provider: FakeSha1PrngProvider, class:
  // >   pw.itr0.selidor.identifier.random.RandomLongIdGeneratorTest$FakeSha1PrngSecureRandomSpi)
  public static final class FakeSha1PrngSecureRandomSpi extends SecureRandomSpi {

    @Override
    protected void engineSetSeed(byte[] seed) {}

    @Override
    protected void engineNextBytes(byte[] bytes) {
      for (int i = 0; i < bytes.length; i++) {
        bytes[i] = (byte) ('0' + (i % 10));
      }
    }

    @Override
    protected byte[] engineGenerateSeed(int numBytes) {
      return new byte[0];
    }
  }
}
