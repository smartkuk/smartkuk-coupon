package smartkuk.api;

import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import smartkuk.model.Coupon;
import smartkuk.service.CouponService;

/**
 * 쿠폰을 발행하고 발행된 쿠폰을 조회하는 컨트롤러
 */
@RestController
@Slf4j
public class CouponController {

  @Autowired
  private CouponService couponService;

  @GetMapping("/coupons/test")
  public Callable<HttpEntity<List<Coupon>>> test() {
    return () -> {
      List<Coupon> couponse = couponService.getCoupons();
      return ResponseEntity.ok(couponse);
    };
  }
}
