# Iteration0 task scripts


ビルド関連のコマンドです。

[egoist/maid: Markdown driven task runner\.](https://github.com/egoist/maid) をインストールして、 `maid <command>` とすることで、コマンドを実行できます。

コマンドの一覧を確認するには、 `maid help` を実行してください。
```

# Initialize repository

## prepare-mvnw

```bash
mvn -N io.takari:maven:0.7.6:wrapper -Dmaven=3.6.1
```
