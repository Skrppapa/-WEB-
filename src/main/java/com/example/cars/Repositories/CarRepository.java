package com.example.cars.Repositories;
import com.example.cars.Models.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Это 3-уровень - Репозиторий
// В репозитории происходит работа с БД
// Репозиторий наследуется от JPA, в котором CRUD методы уже реализованы
// Если метод работает иначе, прописываем реализацию самостоятельно

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findBySold(boolean sold);
    List<Car> findByShowroomIdAndSoldFalse(Long showroomId);
}


