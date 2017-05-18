package com.xs.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by xs on 2017-05-18.
 * 判断当前用户是否有访问权限
 */
@Slf4j
@Service
public class MyAccessDecisionManager implements AccessDecisionManager{

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // TODO  权限 .... >>>
        if (configAttributes == null) {
            return;
        }

        //所请求的资源拥有的权限(一个资源对多个权限)
        Iterator<ConfigAttribute> iterator = configAttributes.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute configAttribute = iterator.next();
            //访问所请求资源所需要的权限
            String needPermission = configAttribute.getAttribute();
            //用户所拥有的权限authentication
            for (GrantedAuthority ga : authentication.getAuthorities()) {
                log.info("-----------PrimaryKey-----------ga.getAuthority()值=" + ga.getAuthority() + "," + "当前类=MyAccessDecisionManager.decide()");
                if (needPermission.equals(ga.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("没有权限访问！");

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
