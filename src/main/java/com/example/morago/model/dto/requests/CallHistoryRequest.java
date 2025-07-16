package com.example.morago.model.dto.requests;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CallHistoryRequest extends PageRequest{

    @Parameter(description = "Показывать только пропущенные звонки", example = "true")
    private Boolean missed;

    @Parameter(description = "Показывать только последние звонки", example = "false")
    private Boolean last;
}
