// Module name component ending not 'Java Letters' are prohibited not to include version numbers to
// module name.
// http://openjdk.java.net/projects/jigsaw/spec/issues/#VersionsInModuleNames
//
// So OpenJDK compiler reports warning, but `pw.itr0` is not intended to represent version.
@SuppressWarnings({"module", "JavaModuleNaming"})
module pw.itr0.selidor.boot.autoconfigure {
  exports pw.itr0.selidor.boot.autoconfigure.http.client.proxy;

  requires pw.itr0.selidor.http.client;
  requires spring.context;
  requires org.slf4j;
  requires spring.core;
}
