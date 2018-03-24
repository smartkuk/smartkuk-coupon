package smartkuk.service;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import smartkuk.model.Coupon;
import smartkuk.repository.CouponRepository;
import smartkuk.util.CouponGenerator;

@RunWith(SpringRunner.class)
@Slf4j
public class CouponServiceTest {

  @TestConfiguration
  static class CouponServiceConfiguration {
    @Bean
    public CouponService couponService() {
      return new CouponServiceImpl();
    }
  }

  @Autowired
  private CouponService couponService;

  @MockBean
  private CouponRepository couponRepository;

  private Coupon createEntity() {
    Coupon coupon = new Coupon();
    coupon.setEmail(RandomStringUtils.random(3) + "@" + RandomStringUtils.random(3));
    coupon.setCoupon(couponService.toDashStyle(CouponGenerator.generatePerCall()));
    return coupon;
  }

  @Test
  public void getCouponsTest() {

    List<Coupon> coupons = new ArrayList<Coupon>();
    coupons.add(createEntity());
    coupons.add(createEntity());
    coupons.add(createEntity());
    coupons.add(createEntity());
    coupons.add(createEntity());

    PageRequest pageable = new PageRequest(0, 10);
    Page<Coupon> givenResults = new PageImpl<>(coupons, pageable, 5);
    when(couponRepository.findAll(pageable)).thenReturn(givenResults);

    Page<Coupon> results = couponService.getCoupons(pageable);
    log.info("results: {} {}", results, results.getContent());
    assertTrue(results.hasContent());
  }
}
