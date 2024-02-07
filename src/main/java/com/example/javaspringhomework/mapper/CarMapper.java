package com.example.javaspringhomework.mapper;

import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.entity.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper {

   public CarDTO convertToDTO (Car car){
       return CarDTO.builder()
               .id(car.getId())
               .producer(car.getProducer())
               .model(car.getModel())
               .power(car.getPower())
               .photoName(car.getPhotoName())
               .build();
   }

   public Car convertFromDTO (CarDTO carDTO){
       Car car = new Car();
       car.setId(carDTO.getId());
       car.setProducer(carDTO.getProducer());
       car.setModel(carDTO.getModel());
       car.setPower(carDTO.getPower());
       car.setPhotoName(carDTO.getPhotoName());

       return car;
   }
}
