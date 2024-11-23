package com.example.cars.Repositories;
import com.example.cars.Models.Customer;
import com.example.cars.Models.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByShowroomId(Long showroomId);
}
