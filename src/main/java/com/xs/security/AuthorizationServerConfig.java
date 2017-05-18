package com.xs.security;

import com.alibaba.druid.pool.DruidDataSource;
import com.xs.config.DruidAutoConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@AutoConfigureAfter(DruidAutoConfig.class)
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

//    @Autowired
//    private DruidProperties properties;
    @Autowired
    private DruidDataSource dataSource;
    @Autowired
    private MyUserDetailService userDetailService;

//    @Bean
//    public DataSource dataSource() {
//        dataSource = new DruidDataSource();
//        dataSource.setUrl(properties.getUrl());
//        dataSource.setUsername(properties.getUsername());
//        dataSource.setPassword(properties.getPassword());
//        dataSource.setInitialSize(properties.getInitialSize());
//        dataSource.setMinIdle(properties.getMinIdle());
//        dataSource.setMaxActive(properties.getMaxActive());
//        dataSource.setTestOnBorrow(properties.isTestOnBorrow());
//        try {
//            dataSource.setFilters(properties.getFilters());
//            dataSource.init();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return dataSource;
//    }

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;


    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetails());
        /*clients
                .inMemory()
                .withClient("client")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("app")
                .secret("security");*/
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailService);
    }

    @Bean
    public TokenStore tokenStore() {
   //     if (dataSource == null) dataSource();
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public ClientDetailsService clientDetails() {
    //    if (dataSource == null) dataSource();
        return new JdbcClientDetailsService(dataSource);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore);
        return tokenServices;
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }

}