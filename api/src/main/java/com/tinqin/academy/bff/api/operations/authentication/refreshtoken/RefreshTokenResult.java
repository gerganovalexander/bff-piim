package com.tinqin.academy.bff.api.operations.authentication.refreshtoken;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinqin.academy.bff.api.generics.OperationResult;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenResult implements OperationResult {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;
}
