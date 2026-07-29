package com.panonit.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
public class SecurityConfiguration {

    private final static String USER_ROLE = "USER";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        var users = Set.of(
                User.withUsername("admin").password(passwordEncoder.encode("admin")).roles(USER_ROLE).build(),
                User.withUsername("panonit").password(passwordEncoder.encode("password")).roles(USER_ROLE).build()
        );
        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    SecurityFilterChain springSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .oauth2AuthorizationServer(oauth -> oauth.oidc(Customizer.withDefaults()))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .formLogin(Customizer.withDefaults())
                .build();
    }
}
