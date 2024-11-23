package com.example.cars.Service.impl;

import com.example.cars.Models.Seller;
import com.example.cars.Repositories.SellerRepository;
import com.example.cars.Service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    @Override
    public List<Seller> findByShowroomId(Long showroomId) { // Манагеры из выбранного автосалона
        return sellerRepository.findByShowroomId(showroomId);
    }

    @Override
    public Optional<Seller> findById(Long id) {
        return sellerRepository.findById(id);
    }

    @Override
    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

    @Override
    public void delete(Long id) {
        sellerRepository.deleteById(id);
    }
}
