package smartkuk.service;

import java.util.ArrayList;

import org.assertj.core.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import smartkuk.model.Coupon;
import smartkuk.repository.CouponRepository;
import smartkuk.util.CouponGenerator;

@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

  /**
   * 쿠폰 정보을 엑세스하는 리파지토리
   */
  @Autowired
  private CouponRepository couponRepository;

  @Override
  public Page<Coupon> getCoupons(Pageable pageable) {

    log.info("getCoupons 시작");
    log.debug("pageable: {}", pageable);

    if (pageable == null) {
      log.warn("페이지 조건이 없습니다.");
      return new PageImpl<>(new ArrayList<Coupon>());
    }

    Page<Coupon> results = couponRepository.findAll(pageable);

    log.debug("results: {}", results);
    log.info("getCoupons 종료");

    return results;
  }

  @Override
  public void saveCoupon(String email) {

    log.info("사용자의 쿠폰을 발행후 저장 시작");

    if (Strings.isNullOrEmpty(email)) {
      throw new IllegalArgumentException("이메일 주소는 필수 입력값 입니다.");
    }

    if (exist(email)) {
      throw new IllegalArgumentException("이메일(" + email + ") 주소는 발행한 내역이 있습니다.");
    }

    Coupon toSavedEntity = new Coupon();
    toSavedEntity.setEmail(email);
    saveRetry(toSavedEntity);

    log.info("사용자의 쿠폰을 발행후 저장 종료");
  }

  /**
   * 쿠폰 엔티티 객체에 쿠폰번호를 생성하여 셋팅하고 db에 저장한다.
   * 만약 실패하면 1초 마다 20회까지 재시도를 수행
   * FailedGenerationException 예외가 발생하면 재시도를 하지 않음
   *
   * @param coupon 쿠폰 모델 객체
   */
  @Retryable(maxAttempts = 20, backoff = @Backoff(delay = 1000), label = "Retryable coupon persist",
      exclude = { FailedGenerationException.class })
  private void saveRetry(Coupon coupon) {
    log.info("saveRetry 시작");
    coupon.setCoupon(toDashStyle(CouponGenerator.generatePerCall()));
    couponRepository.save(coupon);
    log.info("saveRetry 종료");
  }

  /**
   * 이미 저장한 이메일 검사
   *
   * @param email 문자열 이메일주소
   * @return 존재하면 true
   */
  private boolean exist(String email) {
    boolean exist = couponRepository.countByEmail(email).orElse(0L) > 0L;
    log.debug("이메일: {} / 존재여부: {}", email, exist);
    return exist;
  }

  /**
   * 쿠폰 생성기에서 만들어진 문자 배열을 대쉬가 포함된 문자열로 전환
   *
   * @param coupon 문자 배열
   * @return AAAA-DJEU-83eD-12MD 형태의 문자열
   */
  @Override
  public String toDashStyle(char[] coupon) {

    log.debug("쿠폰: {}", coupon);

    if (coupon == null || coupon.length != 16) {
      throw new FailedGenerationException("생성된 쿠폰이 올바르지 않습니다.");
    }

    char[] toBeConvertedCoupon = new char[19];

    for (int i = 0, p = 0; i < coupon.length; i++) {

      // 대쉬를 추가할 인덱스 4,8,12
      if (i > 0 && i % 4 == 0) {
        toBeConvertedCoupon[p++] = '-';
      }

      toBeConvertedCoupon[p++] = coupon[i];
    }

    String prettyCoupon = new String(toBeConvertedCoupon);

    log.debug("변환된 쿠폰: {}", prettyCoupon);

    return prettyCoupon;
  }
}
