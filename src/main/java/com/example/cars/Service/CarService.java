package com.example.cars.Service;

import com.example.cars.Models.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {

    List<Car> findAll();

    Optional<Car> findById(Long id);

    Car save(Car car);

    List<Car> findBySoldStatus(boolean sold); // Отображаем только не купленные

    List<Car> findByShowroomIdAndSoldStatus(Long showroomId, boolean sold); // Сортировка по Автосалону и непроданности

    void delete(Long id);
}
