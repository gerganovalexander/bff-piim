package com.tinqin.academy.bff.business.config;

import com.tinqin.academy.bff.business.exceptions.EntityDuplicateException;
import com.tinqin.academy.bff.business.exceptions.EntityNotFoundException;
import com.tinqin.academy.bff.business.exceptions.InvalidParametersException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        Response.Body responseBody = response.body();
        HttpStatus responseStatus = HttpStatus.valueOf(response.status());

        if (responseStatus.isSameCodeAs(HttpStatus.NOT_FOUND)) {
            return new EntityNotFoundException(responseBody.toString());
        }
        else if (responseStatus.isSameCodeAs(HttpStatus.CONFLICT)) {
            return new EntityDuplicateException(responseBody.toString());
        }
        else if (responseStatus.isSameCodeAs(HttpStatus.BAD_REQUEST)) {
            return new InvalidParametersException(responseBody.toString());
        }
        else {
            return new RuntimeException("Generic exception");
        }
    }
}
