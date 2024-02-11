package com.example.javaspringhomework.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarDTO {

    ObjectId id;

    String model;

    String producer;

    int power;
}
