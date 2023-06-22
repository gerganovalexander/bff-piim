package com.tinqin.academy.bff.api.simpleGameSearch;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleGameSearchInput implements OperationInput {
    private List<Long> ids;

    Integer page;

    Integer size;
}
