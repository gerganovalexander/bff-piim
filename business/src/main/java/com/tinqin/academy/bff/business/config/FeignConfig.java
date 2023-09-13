package com.tinqin.academy.bff.business.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.academy.discussion.restexport.DiscussionApiClient;
import com.tinqin.academy.piim.restexport.PiimApiClient;
import com.tinqin.beys.generali.pocbe.restexport.FeignInterface;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Bean(name = "DiscussionApiClient")
    public DiscussionApiClient getDiscussionApiClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(customErrorDecoder)
                .target(DiscussionApiClient.class, "http://localhost:8100");
    }

    @Bean(name = "FeignInterface")
    public FeignInterface getFeignClient() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return Feign.builder()
                .encoder(new JacksonEncoder(objectMapper))
                .decoder(new JacksonDecoder(objectMapper))
                .errorDecoder(customErrorDecoder)
                .target(FeignInterface.class, "http://localhost:8085");
    }
}
