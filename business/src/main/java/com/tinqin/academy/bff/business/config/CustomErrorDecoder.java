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
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if(responseStatus.isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return new PiimClientException("Resource not found.", response.status());
        }
        if(responseStatus.isSameCodeAs(HttpStatus.CONFLICT)) {
            return new PiimClientException("Resource already exists.", response.status());
        }
        if(responseStatus.isSameCodeAs(HttpStatus.BAD_REQUEST)) {
            return new PiimClientException("Invalid request.", response.status());
        }
        return new PiimClientException("Oops, something went wrong :(", response.status());
    }
}
