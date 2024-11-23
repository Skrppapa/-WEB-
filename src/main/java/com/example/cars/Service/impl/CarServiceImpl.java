package com.example.cars.Service.impl;

import com.example.cars.Models.Car;
import com.example.cars.Models.MyUser;
import com.example.cars.Repositories.CarRepository;
import com.example.cars.Repositories.UserRepository;
import com.example.cars.Service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {   // Car автоматически внедряется Spring - ом
        this.carRepository = carRepository;                // Инъекция через конструктор
    }

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> findBySoldStatus(boolean sold) {
        return carRepository.findBySold(sold);
    }

    @Override // Возвращаем не проданные автомобили по конкретному автосалону
    public List<Car> findByShowroomIdAndSoldStatus(Long showroomId, boolean sold) {
        return carRepository.findByShowroomIdAndSoldFalse(showroomId);
    }
}
