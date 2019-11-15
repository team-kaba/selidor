package pw.itr0.selidor.sandbox.app.crid;

import java.util.Arrays;
import java.util.Base64;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pw.itr0.selidor.identifier.IdGenerator;
import pw.itr0.selidor.identifier.crid.Crid;
import pw.itr0.selidor.identifier.random.LongId;

@RestController
@RequestMapping("/id")
public class IdGeneratingController {

  private final IdGenerator<Crid> crid;
  private final IdGenerator<LongId> longId;

  public IdGeneratingController(IdGenerator<Crid> crid, IdGenerator<LongId> longId) {
    this.crid = crid;
    this.longId = longId;
  }

  @PostMapping("/crid")
  public String next() {
    return crid.next().toString();
  }

  @GetMapping("/crid/{string}")
  public String get(@PathVariable String string) {
    final Crid crid = this.crid.parse(string);
    return String.join(
        ", ",
        String.valueOf(crid.timestamp()),
        Base64.getEncoder().encodeToString(crid.randomness()),
        crid.uuid().toString());
  }

  @PostMapping("/long")
  public String nextLong() {
    return longId.next().toString();
  }

  @GetMapping("/long/{string}")
  public String getLongId(@PathVariable String string) {
    final LongId id = this.longId.parse(string);
    return String.join(", ", id.toString(), Arrays.toString(id.bytes()));
  }
}
