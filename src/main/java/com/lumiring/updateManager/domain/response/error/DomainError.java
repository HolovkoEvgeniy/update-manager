package com.lumiring.updateManager.domain.response.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lumiring.updateManager.domain.constant.Code;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DomainError {

    private Code code;
    private String message;
    private Map<String, String> errors;
}
