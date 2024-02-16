package com.example.javaspringhomework.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String details;
    private long timestamp;
}
