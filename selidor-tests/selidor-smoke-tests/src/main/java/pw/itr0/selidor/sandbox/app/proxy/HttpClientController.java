package pw.itr0.selidor.sandbox.app.proxy;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request.Builder;
import okhttp3.ResponseBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/proxy")
public class HttpClientController {

  private final OkHttpClient okhttp3;
  private final WebClient webClient;

  public HttpClientController(OkHttpClient client, WebClient.Builder builder) {
    this.okhttp3 = client;
    this.webClient = builder.build();
  }

  @GetMapping("/okhttp")
  String okhttp(@RequestParam String uri) throws IOException {
    final Call call = okhttp3.newCall(new Builder().url(uri).build());
    final ResponseBody body = call.execute().body();
    return body != null ? body.string() : null;
  }

  @GetMapping("/web-client")
  Mono<String> webClient(@RequestParam String uri) {
    return webClient.get().uri(uri).exchangeToMono(response -> response.bodyToMono(String.class));
  }
}
