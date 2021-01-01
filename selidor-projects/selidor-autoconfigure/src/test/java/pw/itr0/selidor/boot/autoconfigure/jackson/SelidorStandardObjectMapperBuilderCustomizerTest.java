package pw.itr0.selidor.boot.autoconfigure.jackson;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.logging.ConditionEvaluationReportLoggingListener;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

@ExtendWith(SoftAssertionsExtension.class)
class SelidorJacksonAutoConfigurationTest {

  private final ApplicationContextRunner context =
      new ApplicationContextRunner()
          .withInitializer(new ConditionEvaluationReportLoggingListener(LogLevel.INFO));

  @Test
  void testSelidorStandardJacksonConfiguration(SoftAssertions s) {
    context
        .withConfiguration(
            AutoConfigurations.of(
                JacksonAutoConfiguration.class, SelidorJacksonAutoConfiguration.class))
        .run(
            ctx -> {
              assertThat(ctx).hasSingleBean(ObjectMapper.class);
              final ObjectMapper sut = ctx.getBean(ObjectMapper.class);
              final ZonedDateTime now =
                  ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Asia/Tokyo"));
              final String serialized = sut.writeValueAsString(now);
              s.assertThat(serialized)
                  .isEqualTo(String.format("\"%s\"", ISO_OFFSET_DATE_TIME.format(now)));
              final ZonedDateTime deserialized = sut.readValue(serialized, ZonedDateTime.class);
              s.assertThat(deserialized).isEqualTo(now);
            });
  }
}
