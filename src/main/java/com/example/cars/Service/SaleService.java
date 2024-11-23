package com.example.cars.Service;

import com.example.cars.Models.Customer;
import com.example.cars.Models.Sale;

import java.util.List;
import java.util.Optional;

public interface SaleService {

    List<Sale> findAll();

    List<Sale> findByShowroomId(Long showroomId);

    Optional<Sale> findById(Long id);

    Sale save(Sale sale);

    void delete(Long id);
}
