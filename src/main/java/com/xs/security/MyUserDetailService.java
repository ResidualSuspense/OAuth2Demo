package com.xs.security;

import com.xs.domain.*;
import com.xs.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xs on 2017-05-18.
 */
@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Autowired
    ResourceDao resourceDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user =userDao.findByName(userName);
        if(user==null){
            throw  new UsernameNotFoundException("UserName " + userName + " not found");
        }
        //获取用户权限
        Collection<GrantedAuthority> grantedAuths=obtionGrantedAuthorities(user);
        Set<GrantedAuthority> grantedAuthorities=new HashSet<>();
        for(Role role:user.getRoles()){
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        // 封装成spring security的user
        UserDetails  userDetail = new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                true,//是否可用
                true,//是否过期
                true,//证书不过期为true
                true,//账户未锁定为true ,
                grantedAuthorities);
        return userDetail;
    }

    // 取得用户的权限
    private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {
        List<Resource> resources = new ArrayList<Resource>();
        //获取用户的角色
        Set<Role> roles = user.getRoles();
        for (Role role : roles) {
            Set<Resource> res = role.getResources();
            for (Resource resource : res) {
                resources.add(resource);
            }
        }
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        for (Resource r : resources) {
            //用户可以访问的资源名称（或者说用户所拥有的权限）
            authSet.add(new SimpleGrantedAuthority(r.getValue()));
        }
        return authSet;
    }
}
