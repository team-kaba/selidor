// Module name component ending not 'Java Letters' are prohibited not to include version numbers to
// module name.
// http://openjdk.java.net/projects/jigsaw/spec/issues/#VersionsInModuleNames
//
// So OpenJDK compiler reports warning, but `pw.itr0` is not intended to represent version.
@SuppressWarnings({"module", "JavaModuleNaming"})
module pw.itr0.selidor.type.mapstruct {
  exports pw.itr0.selidor.type.mapstruct;

  // MapStructのなかでjava.sqlを利用しているのでrequiresが必要。IntelliJではそこまで確認しないためredundant requireとして警告される。
  // noinspection Java9RedundantRequiresStatement
  requires java.sql;
  requires pw.itr0.selidor.identifier;
  requires pw.itr0.selidor.type;
  requires org.mapstruct;
}
