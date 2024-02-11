package com.example.javaspringhomework.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("cars")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Car {

    @MongoId
    ObjectId id;

    String model;

    String producer;

    int power;

}
