package smartkuk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

/**
 * 재시도 처리를 위한 환경설정
 */
@Configuration
@EnableRetry
public class RetryConfiguration {

}
