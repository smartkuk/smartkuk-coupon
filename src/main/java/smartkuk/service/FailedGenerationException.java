package smartkuk.service;

/**
 * CouponGenerator 쿠폰번호 생성결과에 문제가 있으면 발생함
 */
public class FailedGenerationException extends RuntimeException {

  private static final long serialVersionUID = 1262836139259582791L;

  public FailedGenerationException(String message) {
    super(message);
  }

}
