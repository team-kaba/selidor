package pw.itr0.sandbox.app.primer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/primer/hello")
public class HelloWorldRestController {
  @GetMapping
  public String hello() {
    return "Hello, World!";
  }
}
