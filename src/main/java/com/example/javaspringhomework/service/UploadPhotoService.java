package com.example.javaspringhomework.service;

import com.example.javaspringhomework.dto.CarDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadPhotoService {


    private final CarService carService;


    @SneakyThrows
    @Transactional
    public CarDTO download(String photoName, String model, String producer, int power){

        CarDTO carDTO = CarDTO.builder()
                .photoName(photoName)
                .model(model)
                .producer(producer)
                .power(power)
                .build();

        return carService.create(carDTO);
    }

}
