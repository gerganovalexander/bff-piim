package com.tinqin.academy.bff.business.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@RequiredArgsConstructor
public class FeignConfig {
    private final CustomErrorDecoder customErrorDecoder;

    @Bean(name = "PiimApiClient")
    public PiimApiClient getPiimApiClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(customErrorDecoder)
                .target(PiimApiClient.class, "http://localhost:8080");
    }
}
