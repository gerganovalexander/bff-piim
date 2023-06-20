package com.tinqin.academy.bff.business.config;

import com.tinqin.academy.piim.restexport.PiimApiClient;
import feign.Feign;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    @Bean
    public PiimApiClient getPiimApiClient(){
        return Feign.builder()
                .target(PiimApiClient.class,"http://localhost:8080");
    }
}
