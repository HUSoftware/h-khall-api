package h.dao.jdbc;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

@Configuration
@PropertySource("classpath:db.properties")
public class DbConnect
{
  @Value("${local}")
  private boolean mLocal;

  @Value("${${prefix}.driverclass}")
  private String mDriverClass;

  @Value("${${prefix}.url}")
  private String jdbcUrl;

  @Value("${${prefix}.username}")
  private String mUserName;

  @Value("${${prefix}.password}")
  private String mPassword;

  @Value("${${prefix}.jndiname}")
  private String mJndiName;

  @Bean
  @Primary
  public DataSource dataSource()
  {
    if (mLocal)
    {
      return DataSourceBuilder.create().username(mUserName).password(mPassword).url(jdbcUrl).driverClassName(mDriverClass).build();
    }
    else
    {
      return new JndiDataSourceLookup().getDataSource(mJndiName);
    }
  }
}