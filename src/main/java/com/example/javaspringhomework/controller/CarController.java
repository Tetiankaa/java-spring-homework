package com.example.javaspringhomework.controller;

import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.exceptions.Error;
import com.example.javaspringhomework.service.CarService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("cars")
public class CarController {

    private final CarService carService;


    @GetMapping("")
    public ResponseEntity<List<CarDTO>> getCars(){
        return new ResponseEntity<>(carService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable ObjectId id) throws Error {
        return new ResponseEntity<>(carService.getById(id),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO car){
        return new ResponseEntity<>(carService.save(car),HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable ObjectId id){
        carService.delete(id);
        return ResponseEntity.accepted().build();
    }
}
