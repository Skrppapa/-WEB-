package com.example.cars.Service.impl;

import com.example.cars.Models.Customer;
import com.example.cars.Models.Sale;
import com.example.cars.Repositories.SaleRepository;
import com.example.cars.Service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    @Autowired
    private SaleRepository saleRepository;

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public List<Sale> findByShowroomId(Long showroomId) { // Продажи из выбранного автосалона
        return saleRepository.findByShowroomId(showroomId);
    }

    @Override
    public Optional<Sale> findById(Long id) {
        return saleRepository.findById(id);
    }

    @Override
    public Sale save(Sale sale) {
        return saleRepository.save(sale);
    }

    @Override
    public void delete(Long id) {
        saleRepository.deleteById(id);
    }
}
