package com.shop.polyedrum.config;

import com.shop.polyedrum.domain.Role;
import com.shop.polyedrum.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter authFilter;
    private final AuthenticationProvider authenticationProvider;
    @Value("${cors.cross-origin}")
    private String origin;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(DELETE, "/api/v1/user/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(POST, "/api/v1/products/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(PUT, "/api/v1/products/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(DELETE, "/api/v1/products/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(POST, "/api/v1/genres/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(DELETE, "/api/v1/genres/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(PUT, "/api/v1/genres/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(POST, "/api/v1/categories/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(PUT, "/api/v1/categories/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers(DELETE, "/api/v1/categories/**").hasAnyAuthority(Role.ADMIN.name())
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers(GET, "/api/v1/products/**").permitAll()
                                .requestMatchers(GET, "/api/v1/categories/**").permitAll()
                                .requestMatchers(GET, "/api/v1/genres/**").permitAll()
                                .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin(origin);
        config.setAllowedMethods(Arrays.asList("POST", "PUT", "GET", "DELETE"));
        var source = new UrlBasedCorsConfigurationSource();
        config.setAllowedHeaders(List.of("*"));
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
