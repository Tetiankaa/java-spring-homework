package com.example.javaspringhomework.controllers;

import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.service.CarService;
import com.example.javaspringhomework.utils.View;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping("")
    @JsonView(View.Level3.class)
    public ResponseEntity<List<CarDTO>> getCars(){
        return ResponseEntity.ok(carService.getAll());
    }

    @GetMapping("/{id}")
    @JsonView(View.Level1.class)
    public ResponseEntity<CarDTO> getCar(@PathVariable int id){
        return ResponseEntity.ok(carService.getById(id));
    }

    @PostMapping("")
    @JsonView(View.Level3.class)
    public ResponseEntity<CarDTO> saveCar(@RequestBody @Valid CarDTO car){
        CarDTO savedCar = carService.create(car);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId()).toUri();
        return ResponseEntity.created(uri).body(savedCar);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void removeCar(@PathVariable int id){
        carService.deleteById(id);
    }

    @GetMapping("/power/{value}")
    @JsonView(View.Level2.class)
    public ResponseEntity<List<CarDTO>> getByPower(@PathVariable int value){
        return ResponseEntity.ok(carService.getByPower(value));
    }

    @GetMapping("/producer/{value}")
    @JsonView(View.Level2.class)
    public ResponseEntity<List<CarDTO>> getByProducer(@PathVariable String value){
        return ResponseEntity.ok(carService.getByProducer(value));
    }
}
