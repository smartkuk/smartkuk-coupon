package smartkuk.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 사이트 루트로 요청이 들어오면 index로 이동하는 컨트롤러
 */
@Controller
public class IndexController {
  @RequestMapping(value = "/", method = RequestMethod.GET)
  public String home() {
    return "index.html";
  }
}
