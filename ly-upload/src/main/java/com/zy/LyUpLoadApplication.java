package com.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//注解@EnableEurekaClient上有@EnableDiscoveryClient注解，可以说基本就是EnableEurekaClient有
//@EnableDiscoveryClient的功能，另外上面的注释中提到，其实@EnableEurekaClient注解就是一种方便
//使用eureka的注解而已，可以说使用其他的注册中心后，都可以使用@EnableDiscoveryClient注解，但是
//        使用@EnableEurekaClient的情景，就是在服务采用eureka作为注册中心的时候，使用场景较为单一。
@EnableDiscoveryClient
@SpringBootApplication
public class LyUpLoadApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyUpLoadApplication.class);
    }
}
