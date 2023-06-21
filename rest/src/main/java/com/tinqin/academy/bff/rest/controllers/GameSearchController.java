package com.tinqin.academy.bff.rest.controllers;

import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchOperation;
import com.tinqin.academy.bff.api.simpleGameSearch.SimpleGameSearchResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/game-search")
public class GameSearchController {

    private final SimpleGameSearchOperation simpleGameSearchOperation;

    @PostMapping
    public SimpleGameSearchResult simpleGameSearch(@RequestBody SimpleGameSearchInput input) {
        return simpleGameSearchOperation.process(input);
    }


}
