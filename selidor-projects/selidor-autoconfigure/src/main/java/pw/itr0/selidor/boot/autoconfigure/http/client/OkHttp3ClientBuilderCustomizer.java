package pw.itr0.selidor.boot.autoconfigure.http.client;

import okhttp3.OkHttpClient;

@FunctionalInterface
public interface OkHttp3ClientBuilderCustomizer {
  void customize(OkHttpClient.Builder clientBuilder);
}
