package smartkuk.util;

import java.util.SplittableRandom;

/**
 * 쿠폰번호를 생성하는 클래스
 */
public class CouponGenerator {

  /**
   * 0-9, a-z, A-Z 배열
   */
  private static final char[] ELEMENT_CHARACTERS =
      "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

  /**
   * 랜덤 숫자를 추출시 한계 숫자는 문자 배열의 길이(62)
   */
  private static final int BOUND_NUMBER = ELEMENT_CHARACTERS.length;

  /**
   * 16자리 문자 배열을 생성하고 리턴
   *
   * @return SplittableRandom 클래스를 이용한 랜덤 문자 배열
   */
  public static char[] generatePerCall() {

    // 결과물 문자 배열(16자리)
    char[] coupon = new char[16];

    SplittableRandom splittableRandom = new SplittableRandom();

    for (int i = 0; i < coupon.length; i++) {

      // 문자 배열의 문자 결정
      int elementIndex = splittableRandom.nextInt(BOUND_NUMBER);

      // 순차적으로 랜덤 문자 할당
      coupon[i] = ELEMENT_CHARACTERS[elementIndex];
    }

    return coupon;
  }
}
