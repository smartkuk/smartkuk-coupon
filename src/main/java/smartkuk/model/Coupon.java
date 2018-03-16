package smartkuk.model;

import java.io.Serializable;
import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coupon")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Coupon implements Serializable {

  private static final long serialVersionUID = -7265711111903670175L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private @Setter String email;
  private @Setter String coupon;
  private @CreatedDate Instant createdAt;

}
