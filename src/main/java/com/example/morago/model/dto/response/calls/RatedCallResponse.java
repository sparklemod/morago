package com.example.morago.model.dto.response.calls;

import com.example.morago.model.entity.Call;

public record RatedCallResponse (
        Call call,
        double averageUserRating,
        double averageTranslatorRating
)  {}
