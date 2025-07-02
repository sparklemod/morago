package com.example.morago.model.dto.requests.category;

import com.example.morago.model.dto.requests.PageRequest;
import lombok.Data;

@Data
public class CategoryPageRequest extends PageRequest {
    Boolean isActive;
    String keyword;
}
