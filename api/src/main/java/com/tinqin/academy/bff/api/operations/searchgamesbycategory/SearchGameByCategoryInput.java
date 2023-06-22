package com.tinqin.academy.bff.api.operations.searchgamesbycategory;

import com.tinqin.academy.bff.api.generics.OperationInput;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchGameByCategoryInput implements OperationInput {
    private String categoryName;
    private Integer page;
    private Integer size;
}
