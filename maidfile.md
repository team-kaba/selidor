# Iteration0 task scripts


ビルド関連のコマンドです。

[egoist/maid: Markdown driven task runner\.](https://github.com/egoist/maid) をインストールして、 `maid <command>` とすることで、コマンドを実行できます。

コマンドの一覧を確認するには、 `maid help` を実行してください。


# Static analysis

## owasp-dependency-check

OWASPのdependency-checkを利用して、CVEに脆弱性が報告されているライブラリが依存関係に含まれないかどうかを確認します。

```bash
mvn dependency-check:aggregate
```

# Update dependencies

## check-update

```bash
mvn versions:display-property-updates
```

## apply-update

```bash
mvn versions:update-properties
```

# Initialize repository

## prepare-mvnw

```bash
mvn -N io.takari:maven:0.7.6:wrapper -Dmaven=3.6.1
```
