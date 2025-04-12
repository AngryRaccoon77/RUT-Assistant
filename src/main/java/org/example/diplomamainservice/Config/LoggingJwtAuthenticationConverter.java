package org.example.diplomamainservice.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class LoggingJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private static final Logger log = LoggerFactory.getLogger(LoggingJwtAuthenticationConverter.class);

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        // Логирование issuer и всех claim'ов
        log.info("Получен JWT с issuer: {}", jwt.getIssuer());
        log.info("Claims: {}", jwt.getClaims());

        // Извлекаем authorities из claim "scope"
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        Object scopeClaim = jwt.getClaims().get("scope");

        if (scopeClaim instanceof String) {
            // Если scope приходит как строка, разбиваем по пробелам
            String[] scopes = ((String) scopeClaim).split(" ");
            for (String scope : scopes) {
                // Обычно область добавляется с префиксом "SCOPE_"
                authorities.add(new SimpleGrantedAuthority("SCOPE_" + scope));
            }
        } else if (scopeClaim instanceof Collection<?>) {
            for (Object scopeObj : (Collection<?>) scopeClaim) {
                authorities.add(new SimpleGrantedAuthority("SCOPE_" + scopeObj.toString()));
            }
        }

        // Дополнительно можно извлечь роли из realm_access, если требуется
        Object realmAccessObj = jwt.getClaims().get("realm_access");
        if (realmAccessObj instanceof Map) {
            Map<?, ?> realmAccess = (Map<?, ?>) realmAccessObj;
            Object rolesObj = realmAccess.get("roles");
            if (rolesObj instanceof Collection<?>) {
                for (Object role : (Collection<?>) rolesObj) {
                    // Роли можно добавлять с другим префиксом, например "ROLE_"
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toString()));
                }
            }
        }

        // Создаем аутентификационный токен. Конструктор JwtAuthenticationToken автоматически маркирует его как authenticated,
        // если переданы не пустые GrantedAuthorities.
        JwtAuthenticationToken token = new JwtAuthenticationToken(jwt, authorities);
        log.info("Создан JwtAuthenticationToken с authorities: {}", authorities);
        return token;
    }
}
