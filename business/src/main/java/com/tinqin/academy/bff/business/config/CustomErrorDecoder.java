package com.tinqin.academy.bff.business.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqin.academy.bff.business.exceptions.PiimClientException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;
    @Override
    public Exception decode(final String s, final Response response) {
//        String message = "Generic error message";
//        try {
//            message = objectMapper.readValue(response.body().asInputStream(), String.class);
//        } catch (IOException e) {
//            throw new RuntimeException(e.getMessage());
//        }
        return new PiimClientException(response.body().toString(), response.status());
    }
}
