package org.example.diplomamainservice.Controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.example.diplomamainservice.Config.LoggingAuthenticationEntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(LoggingAuthenticationEntryPoint.class);

    @GetMapping("/public/hello")
    public String publicEndpoint() {
        return "Hello from PUBLIC endpoint!";
    }

    @GetMapping("/secure/hello")
    public String secureEndpoint(Authentication authentication) {
        // Логируем содержимое аутентификации
        log.info("Детали аутентификации: {}", authentication);
        return "Hello from SECURE endpoint (valid token required)!";
    }
}
