package core.configurations;

import com.CloudClub.jpaStudy.repository.AccountRepository;
import com.CloudClub.jpaStudy.service.TransferService;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MockConfiguration {

  @Bean public TransferService transferService(AccountRepository accountRepository) {
    return new TransferService(accountRepository);
  }

  @Bean public AccountRepository accountRepository(DataSource dataSource) {
    return new JdbcAccountRepository(dataSource);
  }

  @Bean public DataSource dataSource() {
    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName("org.h2.Driver");
    dataSource.setUrl("jdbc:h2:mem:testdb");
    dataSource.setUsername("sa");
    dataSource.setPassword("");
    return dataSource;
  }
}
