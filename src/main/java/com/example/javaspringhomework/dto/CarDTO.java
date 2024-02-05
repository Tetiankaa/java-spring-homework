package com.example.javaspringhomework.dto;

import com.example.javaspringhomework.utils.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDTO {

    @JsonView(View.Level1.class)
    int id;

    @JsonView(View.Level3.class)
    @NotBlank(message = "Model cannot be blank")
    @Size(min = 1,max = 50,message = "Model length must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9-]+$",message = "Model can only contain letters, digits, and hyphens")
    String model;

    @JsonView(View.Level3.class)
    @NotBlank(message = "Model cannot be blank")
    @Size(min = 1,max = 50,message = "Model length must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9-]+$",message = "Model can only contain letters, digits, and hyphens")
    String producer;

    @JsonView(View.Level2.class)
    @Max(value = 1000, message = "Power cannot exceed 1000")
    @Min(value = 50, message = "Power must be at least 50")
    int power;
}
