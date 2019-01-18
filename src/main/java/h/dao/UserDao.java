package h.dao;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserDao
{
  UserDetails select(String inUserId) throws UsernameNotFoundException;

  void create(String inUserId, String inPassword);
}