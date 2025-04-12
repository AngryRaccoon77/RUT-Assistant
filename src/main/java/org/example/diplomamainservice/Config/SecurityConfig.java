package org.example.diplomamainservice.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Отключаем CSRF для REST
        http.csrf(AbstractHttpConfigurer::disable);

        // Настраиваем авторизацию: публичные /api/v1/public/** доступны всем, все остальные – только для аутентифицированных
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/public/**").permitAll()
                .anyRequest().authenticated()
        );

        // Подключаем OAuth2 Resource Server с кастомным JwtAuthenticationConverter
        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt.jwtAuthenticationConverter(new LoggingJwtAuthenticationConverter()))
                .authenticationEntryPoint(new LoggingAuthenticationEntryPoint())
        );

        return http.build();
    }
}
