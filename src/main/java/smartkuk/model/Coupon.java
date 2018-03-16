package smartkuk.model;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Coupon {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  private @Setter String email;
  private @Setter String coupon;
  private @CreatedDate Instant createdAt;

}
