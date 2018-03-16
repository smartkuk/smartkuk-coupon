package smartkuk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import smartkuk.model.Coupon;
import smartkuk.repository.CouponRepository;

@Service
@Slf4j
public class CouponServiceImpl implements CouponService {

  @Autowired
  private CouponRepository couponRepository;

  @Override
  public List<Coupon> getCoupons() {
    List<Coupon> coupons = new ArrayList<Coupon>();
    couponRepository.findAll().forEach(coupon -> {
      coupons.add(coupon);
    });
    log.info("coupons: {}", coupons);
    return coupons;
  }

}
