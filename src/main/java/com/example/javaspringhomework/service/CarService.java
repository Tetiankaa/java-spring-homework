package com.example.javaspringhomework.service;

import com.example.javaspringhomework.dao.CarDAO;
import com.example.javaspringhomework.dto.CarDTO;
import com.example.javaspringhomework.entity.Car;
import com.example.javaspringhomework.mapper.CarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public CarDTO create(CarDTO carDTO){
        Car saved = carDAO.save(mapper.convertFromDTO(carDTO));

       mailService.sendEmail(receiver,"Confirmation of saving the car. Here are details about your car: Model - " + saved.getModel() + " ; Producer: " + saved.getProducer() + "; Power: " + saved.getPower() + " !!!","Successfully saved");
        return mapper.convertToDTO(saved);

    }

    public void deleteCar(Integer id ){
        Optional<Car> carOptional = carDAO.findById(id);

        if (carOptional.isPresent()){
            Car car = carOptional.get();

             mailService.sendEmail(receiver,"Confirmation of deleting the car! Here are details about deleted car: Model - " + car.getModel() + "; Producer - " + car.getProducer() + "; Power: " + car.getPower() + " !!!","Successfully deleted");
            carDAO.deleteById(id);
        }else {
            throw new RuntimeException("Car not found with ID " + id);
        }

    }
}
