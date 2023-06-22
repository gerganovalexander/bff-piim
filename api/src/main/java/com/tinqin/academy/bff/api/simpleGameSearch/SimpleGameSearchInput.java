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
    Integer page;
    Integer size;
    private List<Long> ids;
}
