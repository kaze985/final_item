package com.lppnb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Spring Boot Starter
 *
 * @author Frank Zhang
 */
@SpringBootApplication(scanBasePackages = {"com.lppnb", "com.alibaba.cola"}, exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.lppnb.gatewayimpl.database")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.lppnb")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
