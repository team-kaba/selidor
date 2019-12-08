package pw.itr0.selidor.type.mapstruct;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.mapstruct.Qualifier;

/**
 * {@link java.util.UUID} から {@link pw.itr0.selidor.identifier.crid.Crid}
 * の文字列に変換する時に使うMapStructのQualifierです。
 *
 * <p>例えば、次のように利用します。
 *
 * <pre><code class="java">
 * &#064;Mapping(target = "id", qualifiedBy = UUIDToCridString.class)
 * FormOne translateToOne(FormAnother s);
 * </code></pre>
 */
@Qualifier
@Retention(CLASS)
@Target({TYPE, METHOD})
public @interface UUIDToCridString {}
