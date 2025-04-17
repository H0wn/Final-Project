package com.finalproject.airport.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.airport.model.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    @Override
    List<Airport> findAll();
}

