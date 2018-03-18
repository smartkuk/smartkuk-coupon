package smartkuk.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import smartkuk.SmartkukCouponApplication;

@Configuration
//엔티티 스캔
@EntityScan(basePackageClasses = SmartkukCouponApplication.class)
//repository 로딩
@EnableJpaRepositories(basePackageClasses = SmartkukCouponApplication.class)
//JPA Auditing 처리를 위해 추가(orm.xml)
@EnableJpaAuditing
public class JpaConfiguration {}
