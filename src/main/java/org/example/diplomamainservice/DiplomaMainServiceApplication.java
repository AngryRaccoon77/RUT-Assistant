package org.example.diplomamainservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableFeignClients(basePackages = "org.example.diplomamainservice.Client")
public class DiplomaMainServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaMainServiceApplication.class, args);
    }

}
