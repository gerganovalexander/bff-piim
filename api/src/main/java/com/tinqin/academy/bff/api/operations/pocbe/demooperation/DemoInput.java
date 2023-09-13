package com.tinqin.academy.bff.api.operations.pocbe.demooperation;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemoInput implements OperationInput {

    private String id;
}
