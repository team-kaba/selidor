package pw.itr0.selidor.type.mapstruct;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.mapstruct.Qualifier;

/**
 * {@link pw.itr0.selidor.identifier.crid.Crid} の文字列からUUIDに変換する時に使うMapStructのQualifierです。
 *
 * <p>例えば、次のように利用します。
 *
 * <pre><code class="java">
 * &#064;Mapping(target = "id", qualifiedBy = CridStringToUUID.class)
 * FormAnother translateToAnother(FormOne s);
 * </code></pre>
 */
@Qualifier
@Retention(CLASS)
@Target({TYPE, METHOD})
public @interface CridStringToUUID {}
