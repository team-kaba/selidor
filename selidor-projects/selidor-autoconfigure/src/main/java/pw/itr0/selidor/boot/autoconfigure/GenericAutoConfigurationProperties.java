package pw.itr0.selidor.boot.autoconfigure;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Spring Boot の Auto Configuration に対して、有効/無効を切り替えるためのプロパティを予め定義してある抽象クラスです。
 *
 * <p>デフォルト（{@code xxx.yyy.enabled}プロパティが定義されないとき）でも有効になるように、 {@link AutoConfigure#enabled} が {@code
 * null} の場合には {@link AutoConfigure#isEnabled()} が {@code true} を返すようにしています。
 *
 * <p>IDEなどで補完が行えるようにするために定義しているクラスであり、実際には次のように実装するため {@link AutoConfigure#isEnabled()} が {@code
 * false} を返すことはありません。 {@code xxx.yyy.enabled=false} が設定されている場合は、Bean自体が定義されないのが正しいふるまいとなります。
 *
 * <pre><code class="java">
 * &#64;ConditionalOnProperty(
 *     prefix = "example.config.autoconfigure",
 *     name = "enabled",
 *     havingValue = "true",
 *     matchIfMissing = true
 * )
 * &#64;EnableConfigurationProperties(ExampleConfigurationProperties.class)
 * public class ExampleAutoConfiguration {}
 *
 * &#64;ConfigurationProperties("example.config")
 * public class ExampleConfigurationProperties extends GenericAutoConfigurationProperties {}
 * </code></pre>
 */
public abstract class GenericAutoConfigurationProperties {
  @NestedConfigurationProperty private AutoConfigure autoconfigure = new AutoConfigure();

  public AutoConfigure getAutoconfigure() {
    return this.autoconfigure;
  }

  public void setAutoconfigure(AutoConfigure autoconfigure) {
    this.autoconfigure = autoconfigure;
  }

  public static class AutoConfigure {
    private Boolean enabled;

    /**
     * Auto Configuration が有効になっている場合、 {@code true} を返します。
     *
     * <p>デフォルト（{@code xxx.yyy.enabled}プロパティが定義されないとき）では、 {@link AutoConfigure#enabled} が {@code
     * null} となりますが、Auto Configurationは有効になるので、 {@code true} を返すようにしています。
     *
     * @return Auto Configuration が有効になっている場合、 {@code true}.
     */
    public boolean isEnabled() {
      return enabled == null || enabled;
    }

    public void setEnabled(boolean enabled) {
      this.enabled = enabled;
    }
  }
}
