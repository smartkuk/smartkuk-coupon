package smartkuk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = SmartkukCouponApplication.class)
@EnableAutoConfiguration
public class SmartkukCouponApplication {

  public static void main(String[] args) {
    SpringApplication.run(SmartkukCouponApplication.class, args);
  }
}
