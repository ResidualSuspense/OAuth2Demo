package com.xs.security;

import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.web.access.DefaultWebInvocationPrivilegeEvaluator;

/**
 * Created by xs on 2017-05-18.
 * 无效。。可忽略此类
 */
public class CustomWebInvocationPrivilegeEvaluator extends DefaultWebInvocationPrivilegeEvaluator {
    public CustomWebInvocationPrivilegeEvaluator(AbstractSecurityInterceptor securityInterceptor) {
        super(securityInterceptor);
    }
}
