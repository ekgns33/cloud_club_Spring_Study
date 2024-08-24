package core.configurations;

import com.CloudClub.jpaStudy.repository.AccountRepository;
import javax.sql.DataSource;

public class JdbcAccountRepository extends AccountRepository {

  public JdbcAccountRepository(DataSource dataSource) {
    super();
  }
}
