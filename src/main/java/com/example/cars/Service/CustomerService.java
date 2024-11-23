package com.example.cars.Service;

import com.example.cars.Models.Customer;
import com.example.cars.Models.Seller;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<Customer> findAll();

    List<Customer> findByShowroomId(Long showroomId);

    Optional<Customer> findById(Long id);

    Customer save(Customer customer);

    void delete(Long id);
}
