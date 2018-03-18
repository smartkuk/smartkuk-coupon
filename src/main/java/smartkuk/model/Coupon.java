package smartkuk.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coupon", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "email" }),
    @UniqueConstraint(columnNames = { "coupon" }) })
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Coupon implements Persistable<Integer> {

  private static final long serialVersionUID = -7265711111903670175L;

  /**
   * 기본키
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  /**
   * 이메일주소
   */
  private @Setter String email;

  /**
   * 쿠폰번호
   */
  private @Setter String coupon;

  /**
   * 생성시간
   */
  private @CreatedDate Instant createdAt;

  @Override
  public boolean isNew() {
    return id == null;
  }

}
