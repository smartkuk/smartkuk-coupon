package smartkuk.api;

import java.util.concurrent.Callable;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import smartkuk.model.Coupon;
import smartkuk.service.CouponService;

/**
 * 쿠폰을 발행하고 발행된 쿠폰을 조회하는 컨트롤러
 */
@RestController
@Slf4j
public class CouponController {

  /**
   * 쿠폰 발급 요청시 사용되는 데이터 구조 클래스
   */
  @Data
  public static class User {
    private String email;
  }

  /**
   * 쿠폰 업무용 서비스
   */
  @Autowired
  private CouponService couponService;

  /**
   * 저장된 쿠폰들을 페이징 조회
   *
   * @param pageable 페이징 조건
   * @return 페이징된 쿠폰
   */
  @GetMapping("/coupons/page")
  public Callable<HttpEntity<Page<Coupon>>> pagedCoupons(Pageable pageable) {

    log.info("GET /coupons/page");
    log.debug("pageable: {}", pageable);

    return () -> {
      return ResponseEntity.ok(couponService.getCoupons(pageable));
    };
  }

  /**
   * 이메일에 해당하는 쿠폰을 발행하여 저장(재시도 처리)
   *
   * @param user 사용자의 이메일 정보가 담긴 객체
   * @return HTTP 응답만 보냄
   */
  @PostMapping("/coupons/retry")
  public Callable<HttpEntity<Void>> generateCouponWithRetry(@RequestBody User user) {

    log.info("POST /coupons/retry");
    log.info("user: {}", user);

    return () -> {

      if (user == null || Strings.isNullOrEmpty(user.getEmail())) {
        throw new IllegalArgumentException("이메일 주소는 필수 입력값 입니다.");
      }

      couponService.saveCouponWithRetry3(user.getEmail());
      return ResponseEntity.ok().build();
    };
  }
}
