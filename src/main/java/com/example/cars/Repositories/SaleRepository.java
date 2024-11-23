package com.example.cars.Repositories;
import com.example.cars.Models.Customer;
import com.example.cars.Models.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByShowroomId(Long showroomId);
}
