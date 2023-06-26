package com.tinqin.academy.bff.rest.controllers;

import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffInput;
import com.tinqin.academy.bff.api.operations.creategamepatch.CreateGamePatchBffOperation;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryInput;
import com.tinqin.academy.bff.api.operations.searchgamesbycategory.SearchGameByCategoryOperation;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchInput;
import com.tinqin.academy.bff.api.operations.simplegamesearch.SimpleGameSearchOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/games")
public class GameSearchController extends BaseController {

    private final SimpleGameSearchOperation simpleGameSearchOperation;
    private final SearchGameByCategoryOperation searchGameByCategoryOperation;
    private final CreateGamePatchBffOperation createGamePatchBffOperation;

    @PostMapping
    public ResponseEntity<?> simpleGameSearch(@RequestBody SimpleGameSearchInput input) {
        return handleOperation(simpleGameSearchOperation.process(input));
    }

    @GetMapping
    public ResponseEntity<?> searchGameByCategoryName(SearchGameByCategoryInput input) {
        return handleOperation(searchGameByCategoryOperation.process(input));
    }

    @PostMapping("/game-patches")
    public ResponseEntity<?> createGamePatchOperation(@RequestBody CreateGamePatchBffInput input) {
        return handleOperation(createGamePatchBffOperation.process(input));
    }
}
