package com.finalproject.airport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.airport.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

    List<Flight> findByDepartureCityAndArrivalCity(
        String departureCity, String arrivalCity);
}