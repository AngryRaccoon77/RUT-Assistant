package org.example.diplomamainservice.Config;

import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    @Bean
    public Request.Options feignRequestOptions() {
        return new Request.Options(
                120_000, // connectTimeout (2 мин)
                300_000  // readTimeout (5 мин)
        );
    }
}
