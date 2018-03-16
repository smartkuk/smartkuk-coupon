package smartkuk.config;

import java.util.List;
import java.util.Optional;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableJpaRepositories(basePackages = "smartkuk.repository")
public class WebMvcConfiguration extends WebMvcConfigurerAdapter {

  @Autowired
  private Optional<AsyncTaskExecutor> asyncTaskExecutor;

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.setUseSuffixPatternMatch(false);
  }

  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    asyncTaskExecutor.ifPresent(configurer::setTaskExecutor);
  }

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    ObjectMapper objectMapper = null;
    for (HttpMessageConverter<?> converter : converters) {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        if (objectMapper == null) {
          objectMapper = ((MappingJackson2HttpMessageConverter) converter).getObjectMapper();
        } else {
          ((MappingJackson2HttpMessageConverter) converter).setObjectMapper(objectMapper);
        }
      }
    }
  }

  /**
   * h2 콘솔 역할을 하게 되는 WebServlet 객체를 생성하여 서블릿으로 등록
   */
  @Bean
  public ServletRegistrationBean h2servletRegistration() {
    ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
    registration.addUrlMappings("/console/*");
    return registration;
  }

}
