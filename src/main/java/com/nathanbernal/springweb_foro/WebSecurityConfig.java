package com.nathanbernal.springweb_foro;

import org.springframework.security.core.userdetails.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebSecurityConfig {

    String url = "http://localhost:8083";

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl(url).build();
    }

    @Bean
    public SecurityFilterChain secutiryFIlterChain(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/", "/home").permitAll()
                .requestMatchers("/**.css", "/home").permitAll()
                .requestMatchers("/**.js", "/home").permitAll()
                .requestMatchers("/**.jpg", "/home").permitAll()
                .requestMatchers("/assets/**.*", "/home").permitAll()
                .requestMatchers("/**.png", "/home").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .permitAll()
            )
            .logout((logout) -> logout.permitAll());

        return http.build();

    }

    @Bean
    @Description("Creación de usuarios en memoria")
    public UserDetailsService users() {

        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password"))
            .roles("USER")
            .build();
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("password"))
            .roles("ADMIN", "USER")
            .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
