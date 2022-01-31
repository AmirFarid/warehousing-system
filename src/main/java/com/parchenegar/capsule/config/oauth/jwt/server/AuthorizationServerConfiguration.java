package com.parchenegar.capsule.config.oauth.jwt.server;

import com.parchenegar.capsule.config.oauth.jwt.server.props.SecurityProperties;
import com.parchenegar.capsule.service.UserDetailService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.security.KeyPair;

import static java.nio.charset.StandardCharsets.UTF_8;

@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter
{

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;
    private final SecurityProperties securityProperties;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Value("${jwt.clientId}")
    private String clientId;

    @Value("${jwt.client-secret}")
    private String clientSecret;

    @Value("${jwt.accessTokenValidititySeconds}")
    private int accessTokenValiditySeconds;

    @Value("${jwt.authorizedGrantTypes}")
    private String[] authorizedGrantTypes;

    @Value("${jwt.refreshTokenValiditySeconds}")
    private int refreshTokenValiditySeconds;

    @Autowired
    public AuthorizationServerConfiguration(final DataSource dataSource,
                                            final PasswordEncoder passwordEncoder,
                                            final AuthenticationManager authenticationManager,
                                            final SecurityProperties securityProperties,
                                            final UserDetailsService userDetailsService)
    {
        this.dataSource = dataSource;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.securityProperties = securityProperties;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public TokenStore tokenStore()
    {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public DefaultTokenServices tokenServices(final ClientDetailsService clientDetailsService)
    {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setTokenStore(tokenStore());
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setAuthenticationManager(this.authenticationManager);
        return tokenServices;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter()
    {
        SecurityProperties.JwtProperties jwtProperties = securityProperties.getJwt();
        KeyPair keyPair = keyPair(jwtProperties, keyStoreKeyFactory(jwtProperties));

        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setKeyPair(keyPair);
        jwtAccessTokenConverter.setVerifierKey(getPublicKeyAsString());
        return jwtAccessTokenConverter;
    }

    private String getPublicKeyAsString()
    {
        try {
            return IOUtils.toString(securityProperties.getJwt().getPublicKey().getInputStream(), UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void configure(final ClientDetailsServiceConfigurer clients) throws Exception
    {
        clients.jdbc(this.dataSource);
    }

    @Override
    public void configure(final AuthorizationServerEndpointsConfigurer endpoints)
    {
        endpoints.authenticationManager(this.authenticationManager)
                .accessTokenConverter(jwtAccessTokenConverter())
                .userDetailsService(this.userDetailsService)
                .tokenStore(tokenStore());
    }

    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer)
    {
        oauthServer
                .passwordEncoder(this.passwordEncoder)
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    private KeyPair keyPair(SecurityProperties.JwtProperties jwtProperties, KeyStoreKeyFactory keyStoreKeyFactory)
    {
        return keyStoreKeyFactory.getKeyPair(jwtProperties.getKeyPairAlias(), jwtProperties.getKeyPairPassword().toCharArray());
    }

    private KeyStoreKeyFactory keyStoreKeyFactory(SecurityProperties.JwtProperties jwtProperties)
    {
        return new KeyStoreKeyFactory(jwtProperties.getKeyStore(), jwtProperties.getKeyStorePassword().toCharArray());
    }
}