package smartkuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = SmartkukCouponApplication.class)
public class SmartkukCouponApplication {
  public static void main(String[] args) {
    SpringApplication.run(SmartkukCouponApplication.class, args);
  }
}
