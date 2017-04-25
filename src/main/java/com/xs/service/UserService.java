package com.xs.service;

import com.xs.domain.User;
import com.xs.domain.UserDao;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user =userDao.findByName(userName);
        //返回一个SpringSecurity需要的用户对象
        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                CollectionUtils.emptyIfNull(null));
    }


}