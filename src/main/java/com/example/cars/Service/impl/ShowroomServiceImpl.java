package com.example.cars.Service.impl;

import com.example.cars.Models.Showroom;
import com.example.cars.Repositories.ShowroomRepository;
import com.example.cars.Service.ShowroomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShowroomServiceImpl implements ShowroomService {

    private final ShowroomRepository showroomRepository;

    @Override
    public List<Showroom> findAll() {
        return showroomRepository.findAll();
    }

    @Override
    public Optional<Showroom> findById(Long id) {
        return showroomRepository.findById(id);
    }

    @Override
    public Showroom save(Showroom showroom) {
        return showroomRepository.save(showroom);
    }

    @Override
    public void delete(Long id) {
        showroomRepository.deleteById(id);
    }
}
