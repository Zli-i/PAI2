package com.kasyno.kasyno.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.kasyno.kasyno.security.ApplicationUserPermission.*;
import static com.kasyno.kasyno.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/index", "/css/*", "js/*").permitAll()
                .antMatchers("/users").hasAuthority(USER_READ.getPermission())
                .antMatchers("/users/*").hasAuthority(ADMIN_READ.getPermission())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails anna = User.builder()
                .username("anna")
                .password(passwordEncoder.encode("password"))
                .authorities(USER.getGrantedAuthorities())
                .build();

        UserDetails derek = User.builder()
                .username("derek")
                .password(passwordEncoder.encode("password"))
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(anna, derek);
    }
}
