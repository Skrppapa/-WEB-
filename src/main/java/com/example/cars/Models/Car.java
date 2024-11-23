package com.example.cars.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    private String brand;

    @NotNull
    @Size(min = 2)
    private String model;

    @NotNull
    @Min(1886)
    private int year;

    @NotNull
    @Min(0)
    private Double price;

    private String bundling;

    @ManyToOne
    @JoinColumn(name = "showroom_id", nullable = false)
    private Showroom showroom;

    @NotNull
    private Boolean sold = false;

    private String formattedPrice;

}
