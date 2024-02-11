package com.example.javaspringhomework.dao;

import com.example.javaspringhomework.entity.Car;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarDAO extends MongoRepository<Car, ObjectId> {
}
