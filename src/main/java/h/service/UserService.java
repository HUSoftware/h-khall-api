package h.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import h.dao.UserDao;

@Service
public class UserService implements UserDetailsService
{
  @Autowired
  UserDao mUserDao;

  @Override
  public UserDetails loadUserByUsername(String inUserId) throws UsernameNotFoundException
  {
    return mUserDao.select(inUserId);
  }

  public void createUserCredentials(String inUserId, String inPassword)
  {
    mUserDao.create(inUserId, inPassword);
  }
}