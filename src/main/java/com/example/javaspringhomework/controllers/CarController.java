package com.example.javaspringhomework.controllers;

import com.example.javaspringhomework.dao.CarDAO;
import com.example.javaspringhomework.models.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarDAO carDAO;


    @GetMapping("")
    public ResponseEntity<List<Car>> getCars(){
        return new ResponseEntity<>(carDAO.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCar(@PathVariable long id){
        return new ResponseEntity<>(carDAO.findById(id).get(),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        Car savedCar = carDAO.save(car);
        return new ResponseEntity<>(savedCar,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCar(@PathVariable long id){
        carDAO.deleteById(id);
    }

    @GetMapping("/power/{value}")
    public ResponseEntity<List<Car>> getCarsByPower(@PathVariable int value){
        return new ResponseEntity<>(carDAO.findByPower(value),HttpStatus.OK);
    }

    @GetMapping("/producer/{value}")
    public ResponseEntity<List<Car>> getCarsByProducer(@PathVariable String value){
        return new ResponseEntity<>(carDAO.findByProducer(value),HttpStatus.OK);
    }

}
