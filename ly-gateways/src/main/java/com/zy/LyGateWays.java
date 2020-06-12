package com.zy;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringCloudApplication
public class LyGateWays {
    public static void main(String[] args) {
        SpringApplication.run(LyGateWays.class);
    }
}
