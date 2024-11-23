package com.example.cars.Repositories;

import com.example.cars.Models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByName(String username);  // Для соблюдения не нулевого контракта
}
