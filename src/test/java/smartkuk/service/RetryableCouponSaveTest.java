package smartkuk.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.RetryConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import smartkuk.repository.CouponRepository;
import smartkuk.service.RetryableCouponSaveTest.CouponServiceTestConfig;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = { CouponServiceTestConfig.class, RetryConfiguration.class })
@Slf4j
public class RetryableCouponSaveTest extends JpaEnabledService {

  @Configuration
  static class CouponServiceTestConfig {
    @Bean
    public CouponService couponService() {
      return new CouponServiceImpl();
    }
  }

  @Autowired
  private CouponService couponService;

  @Autowired
  private CouponRepository couponRepository;

  /**
   * 병렬 저장 테스트
   * 0899,1000,1000,0904,0106,0651,0743,0994,0109,0091
   * 1000,1000,1000,1000,1000,1000,1000,1000,1000,1000
   */
  @Test
  public void parallelSaveCouponTest() {

    int emailCount = 2000;
    createRandomEmails(emailCount).parallelStream().forEach(email -> {
      couponService.nonSplitSaveCouponWithRetry(email);
    });
  }

  /**
   * 병렬 저장 테스트2
   */
  @Test
  public void parallelSaveCouponTestWithAutoSplit() {

    int emailCount = 10000;
    createRandomEmails(emailCount).parallelStream().forEach(email -> {
      couponService.autoSplitSaveCouponWithRetry(email);
    });
  }

  /**
   * 병렬 저장 테스트3
   */
  @Test
  public void parallelSaveCouponTest3() throws Throwable {

//    int emailCount = 10000;
//    int emailCount = 20000;
//    int emailCount = 30000;

    int emailCount = 100000;// 이메일 갯수
    int threadCount = 50;// 스레드 갯수

    ForkJoinPool smartkukPool = new ForkJoinPool(threadCount);
    smartkukPool.submit(() -> {
      createRandomEmails(emailCount).parallelStream().forEach(email -> {
        couponService.saveCouponWithRetry3(email);
        activeCount();
      });
    }).get();
  }

  private void activeCount() {
    log.info("Executed current thread: {} / active thread count: {}",
        Thread.currentThread().getName(), Thread.activeCount());
  }

  @After
  public void display() {
    Long couponCount = couponRepository.count();
    log.info("-----------------");
    log.info("저장된 쿠폰갯수: {}", couponCount == null ? 0 : couponCount);
    log.info("-----------------");
  }

  /**
   * 임의의 이메일 리스트 생성
   */
  private List<String> createRandomEmails(int count) {

    log.info("생성할 이메일 갯수: {}", count);

    List<String> emails = new ArrayList<>();
    for (int i = 0; i < count; i++) {
      String email = RandomStringUtils.random(3) + "@" + RandomStringUtils.random(3);
      emails.add(email);
    }

    return emails;
  }

}
