package com.udacity.jwdnd.course1.cloudstorage.config;

import com.udacity.jwdnd.course1.cloudstorage.services.DefaultAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.ViewResolver;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private DefaultAuthenticationService authenticationService;
    public SecurityConfig(DefaultAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws  Exception{
        http.authorizeHttpRequests(request -> request.requestMatchers("/signup", "/css/**", "/js/**").permitAll().anyRequest().authenticated());
        http.formLogin().loginPage("/login").permitAll();
        http.formLogin().defaultSuccessUrl("/home",true);
        http.logout(logout -> logout.logoutUrl("/my/logout").logoutSuccessUrl("/login").invalidateHttpSession(true));
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        AuthenticationManagerBuilder authenticationManagerBuilder=http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationService);
        return  authenticationManagerBuilder.build();
    }

}
