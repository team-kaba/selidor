package pw.itr0.selidor.boot.autoconfigure.jackson;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

public class SelidorStandardObjectMapperBuilderCustomizer
    implements Jackson2ObjectMapperBuilderCustomizer {

  @Override
  public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
    jacksonObjectMapperBuilder.propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    jacksonObjectMapperBuilder.serializationInclusion(Include.NON_NULL);
    jacksonObjectMapperBuilder.featuresToDisable(
        SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
        SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS,
        DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE,
        DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }
}
