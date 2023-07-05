package com.tinqin.academy.bff.api.operations.entityoutputmodels;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GamePatchBffOutput {
    private Long id;
    private String version;
    private String description;
    private LocalDate uploadedAt;
    private String gameName;
}
