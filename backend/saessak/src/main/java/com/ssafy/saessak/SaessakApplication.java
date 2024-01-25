package com.ssafy.saessak;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@OpenAPIDefinition(servers = {@Server(url = "/", description = "Default Server URL")})
@EnableFeignClients
@ImportAutoConfiguration(FeignAutoConfiguration.class)
@SpringBootApplication
public class SaessakApplication {
    public static void main(String[] args) {
        SpringApplication.run(SaessakApplication.class, args);
    }
}
