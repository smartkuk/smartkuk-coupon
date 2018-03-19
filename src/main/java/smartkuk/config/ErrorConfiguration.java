package smartkuk.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * 에러 페이지 설정
 */
@Configuration
public class ErrorConfiguration extends ServerProperties {

  @Override
  public void customize(ConfigurableEmbeddedServletContainer container) {
    super.customize(container);
    ErrorPage errorPage = new ErrorPage(HttpStatus.NOT_FOUND, "/error.html");
    container.addErrorPages(errorPage);
  }
}
