package com.tinqin.academy.bff.business.config;

import feign.codec.ErrorDecoder;
import lombok.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignErrorDecoder {
    @Bean
    public ErrorDecoder createCustomErrorDecoder() {
        return new CustomErrorDecoder();
    }
}
