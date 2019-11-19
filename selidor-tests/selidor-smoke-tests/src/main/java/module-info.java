// Module name component ending not 'Java Letters' are prohibited not to include version numbers to
// module name.
// http://openjdk.java.net/projects/jigsaw/spec/issues/#VersionsInModuleNames
//
// So OpenJDK compiler reports warning, but `pw.itr0` is not intended to represent version.
@SuppressWarnings({"module", "JavaModuleNaming"})
module pw.itr0.selidor.smoke.tests {
  requires pw.itr0.selidor.identifier;
  requires spring.boot.autoconfigure;
  requires spring.boot;
  requires spring.context;
  requires spring.web;
  requires java.sql;

  opens pw.itr0.selidor.sandbox.app;
  opens pw.itr0.selidor.sandbox.app.crid;
  opens pw.itr0.selidor.sandbox.app.primer;
}
