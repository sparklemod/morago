package com.example.morago.model.dto.requests.call;

import com.example.morago.model.dto.requests.PageRequest;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallHistoryRequest extends PageRequest {

    @Parameter(description = "Show only missed calls", example = "true")
    private Boolean isMissed;

    @Parameter(description = "Show only last calls", example = "false")
    private Boolean isLast;
}
