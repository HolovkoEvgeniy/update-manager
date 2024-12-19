package com.lumiring.updateManager.domain.response.error;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.lumiring.updateManager.domain.response.Response;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse implements Response {
    @JsonUnwrapped
    private DomainError error;
}
