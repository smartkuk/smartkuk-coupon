package smartkuk.util;

import java.util.ArrayList;
import java.util.List;
import java.util.SplittableRandom;

import lombok.extern.slf4j.Slf4j;

/**
 * 쿠폰번호를 생성하는 클래스
 */
@Slf4j
public class CouponGenerator {

  private static boolean[] switchList =
      new boolean[] { true, false, false, false, false, false, false, false, false, false };

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
   * SplittableRandom 랜덤 클래스
   */
  private static SplittableRandom random = new SplittableRandom();

  /**
   * 쿠폰의 갯수를 입력 받아서 쿠폰번호 문자 배열을 담은 리스트를 리턴
   *
   * @param count 생성하고자 하는 쿠폰 갯수
   * @return 생성하고자 하는 갯수만큼의 쿠폰문자 배열의 리스트
   */
  public static List<char[]> generate(int count) {

    log.info("쿠폰번호 생성을 시작({})", count);

    if (count < 1) {
      return null;
    }

    // 병렬로 돌리기 위해서 쿠폰의 갯수만큼 리스트 구성
    List<char[]> generatedCouponList = new ArrayList<char[]>();

    for (int i = 0; i < count; i++) {
      generatedCouponList.add(generate());
    }

    log.info("쿠폰번호 생성이 종료({})", generatedCouponList.size());

    return generatedCouponList;
  }

  /**
   * 16자리 문자 배열을 생성하고 리턴
   *
   * @return SplittableRandom 클래스를 이용한 랜덤 문자 배열
   */
  public static char[] generate() {

    // 결과물 문자 배열(16자리)
    char[] coupon = new char[16];

    // 랜덤 객체(split 메소드를 이용하여 중복을 최대한 회피)
    random = random.split();

    for (int i = 0; i < coupon.length; i++) {

      // 문자 배열의 문자 결정
      int elementIndex = random.nextInt(BOUND_NUMBER);

      // 순차적으로 랜덤 문자 할당
      coupon[i] = ELEMENT_CHARACTERS[elementIndex];
    }

    return coupon;
  }

  /**
   * 16자리 문자 배열을 생성하고 리턴
   *
   * @return SplittableRandom 클래스를 이용한 랜덤 문자 배열
   */
  public static char[] generate(Boolean withoutSplit) {

    // 결과물 문자 배열(16자리)
    char[] coupon = new char[16];

    if (withoutSplit) {

      for (int i = 0; i < coupon.length; i++) {

        // 문자 배열의 문자 결정
        int elementIndex = random.nextInt(BOUND_NUMBER);

        // 순차적으로 랜덤 문자 할당
        coupon[i] = ELEMENT_CHARACTERS[elementIndex];
      }
    } else {

      // 랜덤 객체(split 메소드를 이용하여 중복을 최대한 회피)
      random = random.split();

      for (int i = 0; i < coupon.length; i++) {

        // 문자 배열의 문자 결정
        int elementIndex = random.nextInt(BOUND_NUMBER);

        // 순차적으로 랜덤 문자 할당
        coupon[i] = ELEMENT_CHARACTERS[elementIndex];
      }
    }

    return coupon;
  }

  /**
   * 16자리 문자 배열을 생성하고 리턴
   *
   * @return SplittableRandom 클래스를 이용한 랜덤 문자 배열
   */
  public static char[] autoSplitGenerate() {

    int switchIndex = 0;
    int dividedNum = Thread.activeCount() / 2000;
    String str = String.valueOf(dividedNum);
    if (str.length() > 1) {
      switchIndex = Integer.parseInt(str.substring(str.length() - 1));
    } else {
      switchIndex = Integer.parseInt(str);
    }

    if (!switchList[switchIndex]) {
      random = random.split();
      switchList[switchIndex] = true;
      log.info("랜덤 클래스가 Split 됩니다(Switch index: {})", switchIndex);
      for (int i = 0; i < switchList.length; i++) {
        if (switchIndex != i) {
          switchList[i] = false;
        }
      }
    }

    // 결과물 문자 배열(16자리)
    char[] coupon = new char[16];

    for (int i = 0; i < coupon.length; i++) {

      // 문자 배열의 문자 결정
      int elementIndex = random.nextInt(BOUND_NUMBER);

      // 순차적으로 랜덤 문자 할당
      coupon[i] = ELEMENT_CHARACTERS[elementIndex];
    }

    return coupon;
  }
}
