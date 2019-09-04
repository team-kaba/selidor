# Iteration0 task scripts

ビルド関連のコマンド集です。

[egoist/maid: Markdown driven task runner\.](https://github.com/egoist/maid) をインストールして、 `maid <command>` とすることで、コマンドを実行できます。

コマンドの一覧を確認するには、 `maid help` を実行してください。


# Maven goals

## format

fmt-maven-plugin でソースコードをフォーマットします。

```bash
./mvnw fmt:format
```

## clean

ビルド成果物を削除します。

```bash
./mvnw clean
printf "[\e[90m$(date +'%T')\e[0m] Deleting local maven repository ('\e[36m.m2/repository\e[0m')..."
echo
rm -rf .m2/repository
printf "[\e[90m$(date +'%T')\e[0m] Deleting intermediate flattened pom file ('\e[36m.flattened-pom.xml\e[0m')..."
echo
find . -name .flattened-pom.xml -exec rm {} \;
```

## verify

clean, fmt:format してから、 verify を実行します。

`fmt:format` と `verify` を一回で指定すると、 `fmt:format` がライフサイクルに含まれて実行されてしまいますが、全モジュールに対して先に `fmt:format` だけは実行しておきたいので、2行に分けておきます。

```bash
./mvnw clean fmt:format
./mvnw verify
```

## verify-without-checks

静的解析をせずに verify を実行します。

```bash
./mvnw clean fmt:format
./mvnw -Ddisable.checks verify
```

## verify-without-coverage

カバレッジを取得せずに verify を実行します。

```bash
./mvnw clean fmt:format
./mvnw -Ddisable.coverage verify
```

## verify-without-checks-and-coverage

静的解析とカバレッジ取得をせずに verify を実行します。

```bash
./mvnw clean fmt:format
./mvnw -Ddisable.checks -Ddisable.coverage verify
```


# Deploy artifacts

## deploy-projects-local

このディレクトリにテスト用のローカルリポジトリ (./.m2/repository) を作成して、そこにデプロイします。

```bash
.ci/scripts/deploy-to-local-repository.sh itr0-projects
```

# Integration tests

## integration-tests

```bash
.ci/scripts/deploy-to-local-repository.sh itr0-projects
./mvnw verify -f itr0-tests/itr0-integration-tests/pom.xml -Drepository=./.m2/repository
```

# Static analysis

## owasp-dependency-check

OWASPのdependency-checkを利用して、CVEに脆弱性が報告されているライブラリが依存関係に含まれないかどうかを確認します。

```bash
./mvnw dependency-check:aggregate
```

# Update dependencies

## check-dependency-update

依存ライブラリや利用しているプラグインなどの更新がないかを確認します。

依存ライブラリや利用しているプラグインの定義をしているプロジェクトだけに絞って実行します。

```bash
./mvnw -pl .,itr0-projects/itr0-dependencies versions:display-property-updates
```

## apply-dependency-update

依存ライブラリや利用しているプラグインの更新をpom.xmlに反映します。

依存ライブラリや利用しているプラグインの定義をしているプロジェクトだけに絞って実行します。

```bash
./mvnw -pl .,itr0-projects/itr0-dependencies versions:update-properties
```

# Initialize repository

## prepare-mvnw

mvnwをセットアップします。

```bash
mvn -N io.takari:maven:0.7.6:wrapper -Dmaven=3.6.1
```

# Test tasks

## run-all-tasks

```bash
maid deploy-projects-local
maid format
maid clean
maid verify
maid verify-without-checks
maid verify-without-coverage
maid verify-without-checks-and-coverage
maid owasp-dependency-check
maid check-dependency-update
maid apply-dependency-update
maid prepare-mvnw
```
