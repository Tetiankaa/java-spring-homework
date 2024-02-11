package com.example.javaspringhomework.controllers;

import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.service.CarService;
import com.example.javaspringhomework.service.UploadPhotoService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {


    private final CarService service;
    private final UploadPhotoService uploadPhotoService;

    @GetMapping("")
    public ResponseEntity<List <CarDTO>> getCars(){
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("")
    public ResponseEntity<CarDTO> saveCar(@RequestBody CarDTO car) throws MessagingException, IOException {
        CarDTO savedCar = service.create(car);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId()).toUri();

        return ResponseEntity.created(uri).body(savedCar);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCar(@PathVariable int id){
           try {
               service.deleteCar(id);
               return ResponseEntity.accepted().build();
           }catch (RuntimeException | MessagingException | IOException e){
               return ResponseEntity.notFound().build();
           }

    }

    @PostMapping("/photo")
    public ResponseEntity<CarDTO> uploadPhoto(@RequestParam("photo")MultipartFile photo,@RequestParam String model, @RequestParam String producer, @RequestParam int power) throws IOException {

        String originalFilename  = photo.getOriginalFilename();

        File fileForPhotos = new File(System.getProperty("user.home") + File.separator + "images" + File.separator + photo.getOriginalFilename());

        photo.transferTo(fileForPhotos);

        return ResponseEntity.ok(uploadPhotoService.download(originalFilename ,model,producer,power));
    }


}
