// Module name component ending not 'Java Letters' are prohibited not to include version numbers to
// module name.
// http://openjdk.java.net/projects/jigsaw/spec/issues/#VersionsInModuleNames
//
// So OpenJDK compiler reports warning, but `pw.itr0` is not intended to represent version.
@SuppressWarnings({"module", "JavaModuleNaming"})
module pw.itr0.selidor.boot.autoconfigure {
  exports pw.itr0.selidor.boot.autoconfigure.http.client;
  exports pw.itr0.selidor.boot.autoconfigure.http.proxy;
  exports pw.itr0.selidor.boot.autoconfigure.jackson;

  opens pw.itr0.selidor.boot.autoconfigure.http.client to
      spring.core;
  opens pw.itr0.selidor.boot.autoconfigure.http.proxy to
      spring.core;
  opens pw.itr0.selidor.boot.autoconfigure.jackson to
      spring.core;

  requires static transitive pw.itr0.selidor.http.client;
  requires static transitive com.fasterxml.jackson.databind;
  requires static okhttp3;
  requires static reactor.core;
  requires static reactor.netty;
  requires static io.netty.codec.http;
  requires static spring.beans;
  requires static spring.boot;
  requires static spring.boot.autoconfigure;
  requires static spring.core;
  requires static spring.context;
  requires static spring.web;
  requires static spring.webflux;
  requires static jsr305;
}
