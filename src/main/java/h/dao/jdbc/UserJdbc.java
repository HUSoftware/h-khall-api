package h.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import h.dao.UserDao;

@Component
public class UserJdbc implements UserDao
{
  private static final Map<String, String> CREDENTIALS = new HashMap<>();

  @Autowired
  BCryptPasswordEncoder mEncoder;

  @Override
  public UserDetails select(String inUserId) throws UsernameNotFoundException
  {
    if (!CREDENTIALS.containsKey(inUserId))
    {
      throw new UsernameNotFoundException(inUserId + " not found.");
    }

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority("admin"));

    return new User(inUserId, CREDENTIALS.get(inUserId), authorities);
  }

  @Override
  public void create(String inUserId, String inPassword)
  {
    CREDENTIALS.put(inUserId, mEncoder.encode(inPassword));
  }

  @PostConstruct
  public void sysUsers()
  {
    create("simeon", "abc123");
    create("nadia", "abc123");
  }
}