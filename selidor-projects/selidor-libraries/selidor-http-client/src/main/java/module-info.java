// Module name component ending not 'Java Letters' are prohibited not to include version numbers to
// module name.
// http://openjdk.java.net/projects/jigsaw/spec/issues/#VersionsInModuleNames
//
// So OpenJDK compiler reports warning, but `pw.itr0` is not intended to represent version.
@SuppressWarnings({"module", "JavaModuleNaming"})
module pw.itr0.selidor.http.client {
  exports pw.itr0.selidor.http.client;
  exports pw.itr0.selidor.http.client.proxy;

  requires transitive pw.itr0.selidor.core;
  requires org.slf4j;
  requires static jsr305;
}
