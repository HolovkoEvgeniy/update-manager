package com.lumiring.updateManager.config;

import com.lumiring.updateManager.security.JwtAuthenticationFilter;
import com.lumiring.updateManager.services.user.JdbcUserDetailService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@AllArgsConstructor
@Configuration
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JdbcUserDetailService jdbcUserDetailService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                // Своего рода отключение CORS (разрешение запросов со всех доменов)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOriginPatterns(List.of("*"));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/actuator/prometheus").permitAll()
                        .requestMatchers("api/v1/auth/**").permitAll()

                        .requestMatchers("api/v1/device/admin/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/profile/admin**").hasRole("ADMIN")
                        .requestMatchers("api/v1/device/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("api/v1/profile/**").hasAnyRole("USER", "ADMIN")

                        .anyRequest().authenticated()
                )


                .sessionManagement(manager -> manager.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(jdbcUserDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }

}
