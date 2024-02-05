package com.example.javaspringhomework.dao;

import com.example.javaspringhomework.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDAO extends JpaRepository<Car,Integer> {

    List<Car> findByPower(int value);

    List<Car> findByProducer(String value);
}
