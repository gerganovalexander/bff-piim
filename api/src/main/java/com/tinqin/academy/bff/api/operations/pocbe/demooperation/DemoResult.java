package com.tinqin.academy.bff.api.operations.pocbe.demooperation;

import com.tinqin.academy.bff.api.generics.OperationResult;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemoResult implements OperationResult {
    private String result;
}
