package pw.itr0.selidor.boot.autoconfigure;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noMethods;
import static org.assertj.core.api.Assertions.assertThat;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

// IntelliJ IDEAで実行するとmoduleが有効な状態でテストが実行されてしまい、
// AnalyzeClasses#packagesに指定したパッケージ配下の全クラスを取得することができないようです。
// （ClassFileImporter#importPackagesなども同様です。プロダクションコード側のクラスが取得できないように見えます。）
// そのため、失敗するはずのテストが、IntelliJ IDEA上では成功してしまいます。
@AnalyzeClasses(
    packages = "pw.itr0.selidor",
    importOptions = {
      ImportOption.DoNotIncludeTests.class,
      ImportOption.DoNotIncludeJars.class,
      ImportOption.DoNotIncludeArchives.class
    })
class AutoConfigurationArchUnitTest {
  // IntelliJ IDEAで発生するような問題が起きていないことを確認するために、テスト対象のクラスが読み込まれていることを確認します。
  @ArchTest
  static void CLASSES_MUST_BE_DETECTED_RECURSIVELY(JavaClasses classes) {
    assertThat(classes).describedAs("このテストは、IntelliJ IDEA上で実行した場合には失敗しても問題ありません。").isNotEmpty();
  }

  @ArchTest
  static final ArchRule CONDITIONAL_ON_CLASS_MUST_BE_ON_TYPE =
      noMethods()
          .should()
          .beAnnotatedWith(ConditionalOnClass.class)
          .as("@ConditionalOnClassは必ずクラスにつけてください")
          .because("@ConditionalOnClassがメソッドについていると、対象クラスが存在しない時にNoClassDefFoundErrorが発生してしまいます");
}
