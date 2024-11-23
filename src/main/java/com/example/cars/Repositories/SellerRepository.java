package com.example.cars.Repositories;
import com.example.cars.Models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    List<Seller> findByShowroomId(Long showroomId); // Для получения менеджеров из опр-го автосалона
}
