package com.tinqin.academy.bff.rest.controllers;

import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game-search")
public class GameSearchController extends BaseController {

    private final SimpleGameSearchOperation simpleGameSearchOperation;

    @PostMapping
    public ResponseEntity<?> simpleGameSearch(@RequestBody SimpleGameSearchInput input) {
        return handleOperation(simpleGameSearchOperation.process(input));
    }
}
