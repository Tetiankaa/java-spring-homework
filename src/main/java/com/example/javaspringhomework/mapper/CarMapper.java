package com.example.javaspringhomework.mapper;

import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.models.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDTO toDTO(Car car){
        return CarDTO.builder()
                .id(car.getId())
                .model(car.getModel())
                .power(car.getPower())
                .producer(car.getProducer())
                .build();
    }

    public Car fromDTO(CarDTO carDTO){
        Car car = new Car();
        car.setModel(carDTO.getModel());
        car.setPower(carDTO.getPower());
        car.setProducer(carDTO.getProducer());

        return car;
    }
}
