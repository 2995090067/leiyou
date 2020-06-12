package com.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tk.mybatis.spring.annotation.MapperScan;

@EnableDiscoveryClient//能够让注册中心能够发现，扫描到改服务
@SpringBootApplication
@MapperScan("com.zy.item.mapper")//不加这个spring boot扫描不到zg mapper
public class LyItemsApplication {
    public static void main(String[] args) {
        SpringApplication.run(LyItemsApplication.class);
    }
}
