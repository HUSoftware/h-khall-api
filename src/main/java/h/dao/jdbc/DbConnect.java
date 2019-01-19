package h.dao.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
@PropertySource("classpath:db.properties")
public class DbConnect
{
  @Value("${jndi}")
  private boolean mJndi;

  @Value("${${prefix}.jndiname}")
  private String mJndiName;

  @Value("${${prefix}.driverclass}")
  private String mDriverClass;

  @Value("${${prefix}.url}")
  private String mJdbcUrl;

  @Value("${${prefix}.username}")
  private String mUserName;

  @Value("${${prefix}.password}")
  private String mPassword;

  @Bean
  public DataSource dataSource()
  {
    if (mJndi)
    {
      return new JndiDataSourceLookup().getDataSource(mJndiName);
    }
    else
    {
      return DataSourceBuilder.create().username(mUserName).password(mPassword).url(mJdbcUrl).driverClassName(mDriverClass).build();
    }
  }
}