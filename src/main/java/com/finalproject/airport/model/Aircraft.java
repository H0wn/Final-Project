package com.finalproject.airport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "AIRCRAFTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String aircraftType;
    
    @Column(name = "PASSENGER_CAPACITY")
    private Integer passengerCapacity;
    
    @Column(name = "CARGO_CAPACITY")
    private Integer cargoCapacity;
}