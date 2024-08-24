package core.configurations;

import com.CloudClub.jpaStudy.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

class ApplicationContextTest {

  private TransferService transferService;

  @BeforeEach
  void setup() {
    ApplicationContext context =
        SpringApplication.run(MockConfiguration.class);
    transferService = context.getBean(TransferService.class);
  }

  @Test
  @Disabled
  void applicationContextTest() {

    ApplicationContext context =
        SpringApplication.run(MockConfiguration.class);
    // Use Bean id, cast needed.
    TransferService ts1 = (TransferService)context.getBean("transferService");

    // Use typed method to avoid casting
    TransferService ts2 = context.getBean("transferService", TransferService.class);

    // No need for bean id if type is unique << recommended
    TransferService service =
        context.getBean(TransferService.class);

    service.transfer(100L, 200L, 300);
  }
}
