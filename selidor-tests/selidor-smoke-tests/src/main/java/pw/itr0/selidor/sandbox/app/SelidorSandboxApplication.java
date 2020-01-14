package pw.itr0.selidor.sandbox.app;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Clock;
import okhttp3.OkHttpClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pw.itr0.selidor.identifier.IdGenerator;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.identifier.crid.CridGenerator;
import pw.itr0.selidor.identifier.random.LongId;
import pw.itr0.selidor.identifier.random.RandomLongIdGenerator;

@SpringBootApplication
public class SelidorSandboxApplication {
  public static void main(String[] args) {
    SpringApplication.run(SelidorSandboxApplication.class, args);
  }

  @Bean
  IdGenerator<Crid> cridGenerator() throws NoSuchAlgorithmException {
    return new CridGenerator(
        Clock.systemDefaultZone(), SecureRandom.getInstance("NativePRNGNonBlocking"));
  }

  @Bean
  IdGenerator<LongId> randomLongGenerator() {
    return new RandomLongIdGenerator();
  }

  @Bean
  OkHttpClient okHttpClient(OkHttpClient.Builder builder) {
    return builder.build();
  }
}
