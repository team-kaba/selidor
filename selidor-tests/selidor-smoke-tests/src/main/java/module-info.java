// Module name component ending not 'Java Letters' are prohibited not to include version numbers to
// module name.
// http://openjdk.java.net/projects/jigsaw/spec/issues/#VersionsInModuleNames
//
// So OpenJDK compiler reports warning, but `pw.itr0` is not intended to represent version.
@SuppressWarnings({"module", "JavaModuleNaming"})
module pw.itr0.selidor.smoke.tests {
  requires transitive pw.itr0.selidor.identifier;
  requires transitive pw.itr0.selidor.boot.autoconfigure;
  requires transitive pw.itr0.selidor.http.client;
  requires transitive com.fasterxml.classmate;
  requires okhttp3;
  requires reactor.core;
  requires spring.boot.autoconfigure;
  requires spring.boot;
  requires spring.context;
  requires spring.web;
  requires spring.webflux;

  opens pw.itr0.selidor.sandbox.app;
  opens pw.itr0.selidor.sandbox.app.crid;
  opens pw.itr0.selidor.sandbox.app.proxy;
}
