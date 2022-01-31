package com.parchenegar.capsule.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
class WebSecurityConfiguration extends WebSecurityConfigurerAdapter
{
    private final DataSource dataSource;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;

    @Autowired
    public WebSecurityConfiguration(final DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception
    {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        if (passwordEncoder == null) {
            this.passwordEncoder = new BCryptPasswordEncoder();
        }

        return passwordEncoder;
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        if (userDetailsService == null) {
            userDetailsService = new JdbcDaoImpl();
            ((JdbcDaoImpl) userDetailsService).setDataSource(dataSource);
        }

        return userDetailsService;
    }
}