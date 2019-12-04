package pw.itr0.selidor.type.mapstruct;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.mapstruct.Qualifier;

@Qualifier
@Retention(CLASS)
@Target({TYPE, METHOD})
public @interface UUIDToCridString {}
