package smartkuk.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import smartkuk.model.Coupon;

public interface CouponRepository extends PagingAndSortingRepository<Coupon, Integer> {

  /**
   * 이메일주소에 맞는 갯수 리턴
   *
   * @param email 문자열 이메일주소
   * @return Optional 갯수
   */
  Optional<Long> countByEmail(@Param("email") String email);
}
