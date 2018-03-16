package smartkuk.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import smartkuk.model.Coupon;

public interface CouponRepository extends CrudRepository<Coupon, Integer> {

  List<Coupon> findAllByEmailLike(@Param("email") String email);

}
