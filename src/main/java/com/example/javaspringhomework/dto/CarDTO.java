package com.example.javaspringhomework.dto;

import com.example.javaspringhomework.utils.View;
import com.fasterxml.jackson.annotation.JsonView;
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
    String model;

    @JsonView(View.Level3.class)
    String producer;

    @JsonView(View.Level2.class)
    int power;
}
