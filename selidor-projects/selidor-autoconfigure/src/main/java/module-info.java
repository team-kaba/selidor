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

  requires pw.itr0.selidor.http.client;
  requires org.jetbrains.annotations;
  requires com.fasterxml.jackson.databind;
  requires okhttp3;
  requires reactor.core;
  requires reactor.netty;
  requires io.netty.codec.http;
  requires spring.beans;
  requires spring.boot;
  requires spring.boot.autoconfigure;
  requires spring.core;
  requires spring.context;
  requires spring.web;
  requires spring.webflux;
}
