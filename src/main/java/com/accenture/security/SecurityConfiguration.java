package com.accenture.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "swagger-ui.html"
                        ).permitAll()
                        .requestMatchers("/client/**").permitAll()
                        .requestMatchers("/administrateur/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/voiture/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/voiture/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/voiture/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/voiture/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                );
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT login, password, 1 FROM utilisateur WHERE login=?");
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("SELECT pseudo, role from utilisateur WHERE login=?");
        return jdbcUserDetailsManager;
    }

}
