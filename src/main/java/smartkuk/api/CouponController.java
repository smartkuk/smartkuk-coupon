package smartkuk.api;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * 쿠폰을 발행하고 발행된 쿠폰을 조회하는 컨트롤러
 */
@RestController
@Slf4j
public class CouponController {

  @GetMapping("/coupons/test")
  public Callable<HttpEntity<Map<String, String>>> test() {

    log.info("start");

    Map<String, String> responseMap = new HashMap<String, String>();
    responseMap.put("name", "smartkuk");
    return () -> {
      return ResponseEntity.ok(responseMap);
    };

  }

  @GetMapping("/coupons/test2")
  public Callable<HttpEntity<String>> test2() {

    log.info("start");

    return () -> {
      return ResponseEntity.ok("18");
    };

  }
}
