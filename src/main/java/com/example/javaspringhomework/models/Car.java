package com.example.javaspringhomework.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;


    @Column
    @NotBlank(message = "Model cannot be blank")
    @Size(min = 1,max = 50,message = "Model length must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9-]+$",message = "Model can only contain letters, digits, and hyphens")
    String model;

    @Column
    @NotBlank(message = "Producer cannot be blank")
    @Size(min = 1,max = 50,message = "Producer length must be between 1 and 50 characters")
    @Pattern(regexp = "^[A-Za-z0-9-]+$",message = "Producer can only contain letters, digits, and hyphens")
    String producer;

    @Column
    @Max(value = 1000, message = "Power cannot exceed 1000")
    @Min(value = 50, message = "Power must be at least 50")
    int power;
}
