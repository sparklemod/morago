package com.example.morago.controller.dto.requests.category;

import com.example.morago.controller.dto.requests.PageRequest;
import lombok.Data;

@Data
public class CategoryPageRequest extends PageRequest {
    Boolean isActive;
    String keyword;
}
