package com.example.javaspringhomework.service;

import com.example.javaspringhomework.dao.CarDAO;
import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.mapper.CarMapper;
import com.example.javaspringhomework.models.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarDAO carDAO;
    private final CarMapper carMapper;

    public List<CarDTO> getAll(){
      return carDAO.findAll()
               .stream()
               .map(carMapper::toDTO)
               .toList();
    }

    public CarDTO getById(int id){
        Car car = carDAO.findById(id).get();
        return carMapper.toDTO(car);
    }

    public CarDTO create(CarDTO carDTO){
        Car car = carMapper.fromDTO(carDTO);
        Car saved = carDAO.save(car);
        return carMapper.toDTO(saved);
    }

    public void deleteById(int id){
        carDAO.deleteById(id);
    }

    public List<CarDTO> getByPower(int power){
        return carDAO.findByPower(power).stream()
                .map(carMapper::toDTO)
                .toList();
    }

    public List<CarDTO> getByProducer(String name){
        return carDAO.findByProducer(name).stream()
                .map(carMapper::toDTO)
                .toList();
    }
}
