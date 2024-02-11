package com.example.javaspringhomework.service;

import com.example.javaspringhomework.dao.CarDAO;
import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.entity.Car;
import com.example.javaspringhomework.mapper.CarMapper;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarDAO carDAO;
    private final CarMapper mapper;
    private final MailService mailService;

    @Value("${spring.mail.username}")
    private String receiver;

    public List<CarDTO> getAll(){
        return carDAO.findAll().stream().map(mapper::convertToDTO).toList();
    }

    public CarDTO create(CarDTO carDTO) throws MessagingException, IOException {
        Car saved = carDAO.save(mapper.convertFromDTO(carDTO));

        Path imagePath = Paths.get(System.getProperty("user.home") + File.separator + "images" + File.separator + saved.getPhotoName());

        mailService.sendEmail(receiver,"Confirmation of saving the car. Here are details about your car: Model - " + saved.getModel() + " ; Producer: " + saved.getProducer() + "; Power: " + saved.getPower() + " !!!","Successfully saved",Optional.of(imagePath), saved.getModel());
        return mapper.convertToDTO(saved);

    }

    public void deleteCar(Integer id ) throws MessagingException, IOException {
        Optional<Car> carOptional = carDAO.findById(id);

        if (carOptional.isPresent()){
            Car car = carOptional.get();

             mailService.sendEmail(receiver,"Confirmation of deleting the car! Here are details about deleted car: Model - " + car.getModel() + "; Producer - " + car.getProducer() + "; Power: " + car.getPower() + " !!!","Successfully deleted",Optional.empty(),car.getModel());
            carDAO.deleteById(id);
        }else {
            throw new RuntimeException("Car not found with ID " + id);
        }

    }
}
