package com.tinqin.academy.bff.rest.controllers;

import com.tinqin.academy.bff.api.operations.pocbe.demooperation.DemoInput;
import com.tinqin.academy.bff.api.operations.pocbe.demooperation.DemoOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/test")
public class TestController extends BaseController {
    private final DemoOperation testOperation;

    @GetMapping
    public ResponseEntity<?> getHelloResult(DemoInput input) {
        return handleOperation(testOperation.process(input));
    }
}
