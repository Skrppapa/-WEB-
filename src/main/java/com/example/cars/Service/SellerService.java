package com.example.cars.Service;

import com.example.cars.Models.Seller;

import java.util.List;
import java.util.Optional;

public interface SellerService {

    List<Seller> findAll();

    List<Seller> findByShowroomId(Long showroomId); // Манагеры из выбранного автосалона

    Optional<Seller> findById(Long id);

    Seller save(Seller seller);

    void delete(Long id);
}
