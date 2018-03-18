package smartkuk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import smartkuk.model.Coupon;

public interface CouponService {

  /**
   * 저장된 쿠폰정보를 페이징 조회
   *
   * @param pageable 페이징 조회 조건
   * @return 페이징 조회결과
   */
  Page<Coupon> getCoupons(Pageable pageable);

  /**
   * 이메일을 사용하여 쿠폰 정보를 저장
   *
   * @param email 저장하고자 하는 이메일 문자열
   */
  void saveCouponWithRetry(String email);

  /**
   * split 처리하지 않는 쿠폰 채번 로직과 쿠폰 정보 저장
   *
   * @param email 저장하고자 하는 이메일 문자열
   */
  void nonSplitSaveCouponWithRetry(String email);

  /**
   * 스레드 갯수 기반 자동 split 처리 쿠폰 채번 로직과 쿠폰 정보 저장
   *
   * @param email 저장하고자 하는 이메일 문자열
   */
  void autoSplitSaveCouponWithRetry(String email);

  String toDashStyle(char[] coupon);

}
