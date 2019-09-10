package pw.itr0.selidor.sandbox.app.primer;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.server.LocalServerPort;
import pw.itr0.selidor.test.boot.ApplicationIntegrationTest;

@ApplicationIntegrationTest
class HelloWorldRestControllerApplicationTest {
  @LocalServerPort private Integer port;

  @Test
  void testHelloWorldRoot() throws IOException {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.level(Level.BODY);
    OkHttpClient client = new Builder().addInterceptor(interceptor).build();

    Request request =
        new Request.Builder()
            .get()
            .url(HttpUrl.get("http://localhost:" + port + "/primer/hello"))
            .build();
    Response response = client.newCall(request).execute();
    assertThat(response.code()).isEqualTo(200);
    //noinspection ConstantConditions
    assertThat(response.body().string()).isEqualTo("Hello, World!");
  }
}
