package com.example.Employee.TestAssignment.config;

import com.example.Employee.TestAssignment.service.impl.UserInfoUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@Slf4j
public class SecurityConfig {

    // authentication
    @Bean
    public UserDetailsService userDetailsService(){
        return new UserInfoUserDetailsService();

        /**
         * now if you try to un the application
         *
         * follow steps are done
         * .> create user
         * >load user from db
         * > authenticate it
         * > but to pass it through you'll need an authenttication Provider
         */
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/v1/user/test-request").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/v1/**").authenticated()
                .and().formLogin().and().build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    /**
     * Authentication Provider is the final step before authenticating a user
     * it will need a username and respective password
     */
}
