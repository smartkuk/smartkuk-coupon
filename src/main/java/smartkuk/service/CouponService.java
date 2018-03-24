package smartkuk.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import smartkuk.model.Coupon;

/**
 * 쿠폰과 관련한 서비스를 제공하는 서비스
 * 페이징조회, 쿠폰 생성 기능이 있다.
 */
public interface CouponService {

  /**
   * 저장된 쿠폰정보를 페이징 조회
   *
   * @param pageable 페이징 조회 조건
   * @return 페이징 조회결과
   */
  Page<Coupon> getCoupons(Pageable pageable);

  /**
   * 이메일 주소를 받아서 검사후 재시도 가능한 메소드를 호출하여 채번 및 저장
   *
   * @param email 저장하고자 하는 이메일 문자열
   */
  void saveCoupon(String email);

  /**
   * 문자 배열을 대쉬가 포함된 쿠폰번호 문자열로 변환
   *
   * @param coupon 채번된 쿠폰 문자 배열
   * @return aaaa-1234-1111-djdj-3333 형태의 문자열
   */
  String toDashStyle(char[] coupon);
}
