package com.kasyno.kasyno.security;

import com.kasyno.kasyno.Oauth2.OAuth2AuthenticationFilter;
import com.kasyno.kasyno.Oauth2.OAuth2LoginSuccessHandler;
import com.kasyno.kasyno.Oauth2.CustomOAuth2UserService;
import com.kasyno.kasyno.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.kasyno.kasyno.jwt.JwtVerifier;
import com.kasyno.kasyno.user.UserRepository;
import com.kasyno.kasyno.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.kasyno.kasyno.security.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public ApplicationSecurityConfig( PasswordEncoder passwordEncoder, UserService userService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.userRepository = userRepository;
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new OAuth2AuthenticationFilter(userService, userRepository), BasicAuthenticationFilter.class)
                .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
                .addFilterAfter(new JwtVerifier(), JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/auth/**").permitAll()
                .antMatchers("/paypal/**").permitAll()
                .antMatchers("/","/index", "/css/*", "js/*", "/login.html").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration/**").permitAll()
                .antMatchers(HttpMethod.GET,"/users").hasAuthority(USER_READ.getPermission())
                .antMatchers(HttpMethod.GET,"/users/*").hasAuthority(USER_READ.getPermission())
                .anyRequest().authenticated();

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");
    }

    @Override
    protected  void configure(AuthenticationManagerBuilder auth) throws  Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);
        return provider;
    }


}