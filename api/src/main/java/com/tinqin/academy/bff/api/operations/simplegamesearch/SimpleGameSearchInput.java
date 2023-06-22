package com.tinqin.academy.bff.api.operations.simplegamesearch;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SimpleGameSearchInput implements OperationInput {
    private Integer page;
    private Integer size;
    private List<Long> ids;
}
