package smartkuk.service;

import static org.springframework.transaction.TransactionDefinition.PROPAGATION_REQUIRES_NEW;

import java.util.function.Supplier;

import javax.persistence.EntityManager;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.val;
import smartkuk.config.JpaConfiguration;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = { JpaConfiguration.class })
public class JpaEnabledService extends AbstractTransactionalJUnit4SpringContextTests {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private PlatformTransactionManager transactionManager;

  @Getter(lazy = true, value = AccessLevel.PRIVATE)
  private final TransactionTemplate txTemplate = createTransactionTemplate();

  private TransactionTemplate createTransactionTemplate() {
    val definition = new DefaultTransactionDefinition(PROPAGATION_REQUIRES_NEW);
    return new TransactionTemplate(transactionManager, definition);
  }

  protected void flush() {
    entityManager.flush();
  }

  /**
   * Detach entities
   */
  protected void detach(Object... entities) {
    for (Object entity : entities) {
      if (entityManager.contains(entity)) {
        entityManager.detach(entity);
      }
    }
  }

  /**
   * Transactional execution in {@code PROPAGATION_REQUIRES_NEW}.
   */
  protected <T> T txExecute(Supplier<T> txCall) {
    return getTxTemplate().execute(t -> txCall.get());
  }

}
