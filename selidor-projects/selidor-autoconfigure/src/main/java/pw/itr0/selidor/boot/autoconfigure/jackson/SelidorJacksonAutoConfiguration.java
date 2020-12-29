package pw.itr0.selidor.boot.autoconfigure.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass({ObjectMapper.class, Jackson2ObjectMapperBuilder.class})
public class SelidorJacksonAutoConfiguration {
  @Bean
  // Spring BootのApplication PropertiesでのJacksonの設定が有効になるように、Listの先頭に入れる。
  @Order(Ordered.HIGHEST_PRECEDENCE)
  Jackson2ObjectMapperBuilderCustomizer selidorStandardObjectMapperBuilderCustomizer() {
    return new SelidorStandardObjectMapperBuilderCustomizer();
  }
}
