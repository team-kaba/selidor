package pw.itr0.selidor.boot.autoconfigure.http.client;

import okhttp3.OkHttpClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(OkHttpClient.class)
@Import({
  OkHttp3ClientConfiguration.Direct.class,
  OkHttp3ClientConfiguration.ProxyAware.class,
  OkHttp3ClientConfiguration.SpringRestTemplate.class
})
public class OkHttp3ClientAutoConfiguration {}
