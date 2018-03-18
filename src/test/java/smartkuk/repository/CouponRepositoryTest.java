package smartkuk.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.Instant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import smartkuk.config.JpaConfiguration;
import smartkuk.model.Coupon;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = JpaConfiguration.class)
@Slf4j
public class CouponRepositoryTest {

  @Autowired
  private CouponRepository couponRepository;

  /**
   * persist 상태 전의 엔티티 객체 리턴
   *
   * @return Coupon 객체
   */
  private Coupon createEntity() {
    Coupon coupon = new Coupon(1, "smartkuk@gmail.com", "1234-1234-1234-1234", Instant.now());
    return coupon;
  }

  @Test
  public void countByEmailTest() {
    Coupon entity = createEntity();
    log.info("entity: {}", entity);
    Coupon savedEntity = couponRepository.save(entity);
    log.info("savedEntity: {}", savedEntity);
    assertTrue(entity.equals(savedEntity));
    assertFalse(savedEntity.isNew());
    assertTrue(couponRepository.countByEmail(entity.getEmail()).isPresent());
    assertTrue(couponRepository.countByEmail(entity.getEmail()).get() > 0L);
    assertFalse(couponRepository.countByEmail("").get() > 0L);
  }
}
