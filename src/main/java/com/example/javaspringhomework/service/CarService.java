package com.example.javaspringhomework.service;

import com.example.javaspringhomework.dao.CarDAO;
import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.entity.Car;
import com.example.javaspringhomework.exceptions.Error;
import com.example.javaspringhomework.mapper.CarMapper;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarMapper mapper;
    private final CarDAO carDAO;

    public List<CarDTO> getAll(){
        List<Car> cars = carDAO.findAll();

       return cars.stream().map(mapper::convertToDto).toList();
    }

    public CarDTO getById(ObjectId id) throws Error {
        Optional<Car> carById = carDAO.findById(id);

        if (carById.isPresent()){
            return mapper.convertToDto(carById.get());
        }else {
            throw new Error("Car with id "+ id + " not found");
        }
    }

    public CarDTO save(CarDTO carDTO){
        Car saved = carDAO.save(mapper.convertToCar(carDTO));

        return mapper.convertToDto(saved);
    }

    public void delete(ObjectId id){
        carDAO.deleteById(id);
    }
}
