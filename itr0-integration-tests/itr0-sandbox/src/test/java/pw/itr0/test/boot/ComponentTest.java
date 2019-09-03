package pw.itr0.test.boot;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.context.MessageSourceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@ExtendWith({SpringExtension.class, SoftAssertionsExtension.class})
// ConversionServiceFactoryBeanを明示的に登録しないと、ConversionServiceはAutowireできない。
@Import({ConversionServiceFactoryBean.class})
// MessageSourceAutoConfiguration.classを利用しなくても、MessageSourceはAutowireできる。ただし、message.propertiesを読み込んでくれない。
@ImportAutoConfiguration({MessageSourceAutoConfiguration.class})
public @interface ComponentTest {}
