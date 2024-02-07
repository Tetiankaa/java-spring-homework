package com.example.javaspringhomework.dto;

import lombok.Builder;
import lombok.Data;

import java.io.File;

@Builder
@Data
public class CarDTO {
    int id;

    String model;

    String producer;

    int power;

    String photoName;
}
