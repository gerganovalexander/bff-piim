package com.tinqin.academy.bff.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableFeignClients(basePackages = {"com.tinqin.academy.bff"})
@ComponentScan(basePackages = {"com.tinqin.academy.bff"})
public class BffPiimApplication {

    public static void main(String[] args) {
        SpringApplication.run(BffPiimApplication.class, args);
    }
}
