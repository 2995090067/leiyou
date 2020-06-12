package com.zy.lyregistry2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class LyResistry {
    public static void main(String[] args) {
        SpringApplication.run(LyResistry.class);
    }
}
