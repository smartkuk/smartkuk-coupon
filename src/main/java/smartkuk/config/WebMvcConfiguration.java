package smartkuk.config;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
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
}
