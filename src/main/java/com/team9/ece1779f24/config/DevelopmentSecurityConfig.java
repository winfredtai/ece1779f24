package com.team9.ece1779f24.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
@Profile("dev") // Activate this configuration only for the 'dev' profile
public class DevelopmentSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)  // Disable basic auth popup
                .formLogin(AbstractHttpConfigurer::disable); // Disable form login

        return http.build();
    }
}