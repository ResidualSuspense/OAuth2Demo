package com.xs.security;

import com.xs.domain.Resource;
import com.xs.domain.ResourceDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.WebResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by xiaosong on 2017/5/17.
 */
@Slf4j
@Service
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

    @Autowired
    private ResourceDao resourceDao;

    /**
     * 加载资源，初始化资源变量
     */
    @PostConstruct
    public void loadResourceDefine() {
        if (resourceMap == null) {
            resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
            List<Resource> resources = resourceDao.findAll();
            for (Resource resource : resources) {
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                ConfigAttribute configAttribute = new SecurityConfig(resource.getValue());
                configAttributes.add(configAttribute);
                resourceMap.put(resource.getUrl(), configAttributes);
            }
        }
        log.info("security info load success!!");
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if(resourceMap==null) loadResourceDefine();
        String requestUrl =((FilterInvocation)o).getRequestUrl();
        return resourceMap.get(requestUrl);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
