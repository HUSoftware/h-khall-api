package h.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import h.service.UserService;

//https://www.baeldung.com/spring-security-authentication-with-a-database

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
  @Autowired
  UserService mUserDetailService;

  @Override
  protected void configure(AuthenticationManagerBuilder inAuth) throws Exception
  {
    inAuth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider()
  {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(mUserDetailService);
    authProvider.setPasswordEncoder(encoder());
    return authProvider;
  }

  @Bean
  public BCryptPasswordEncoder encoder()
  {
    return new BCryptPasswordEncoder(11);
  }
}