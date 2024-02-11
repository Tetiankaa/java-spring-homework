package com.example.javaspringhomework.mapper;

import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

    public CarDTO convertToDto(Car car){
        return CarDTO.builder()
                .id(car.getId())
                .model(car.getModel())
                .power(car.getPower())
                .producer(car.getProducer())
                .build();
    }

    public Car convertToCar(CarDTO carDTO){
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setModel(carDTO.getModel());
        car.setPower(carDTO.getPower());
        car.setProducer(carDTO.getProducer());

        return car;
    }
}
