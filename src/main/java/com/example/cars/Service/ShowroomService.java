package com.example.cars.Service;

import com.example.cars.Models.Showroom;

import java.util.List;
import java.util.Optional;

public interface ShowroomService {

    List<Showroom> findAll();

    Optional<Showroom> findById(Long id);

    Showroom save(Showroom showroom);

    void delete(Long id);
}
