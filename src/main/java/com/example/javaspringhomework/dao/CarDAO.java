package com.example.javaspringhomework.dao;

import com.example.javaspringhomework.models.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDAO extends JpaRepository<Car,Long> {
    List<Car> findByPower(int power);

    List<Car> findByProducer(String producer);
}
