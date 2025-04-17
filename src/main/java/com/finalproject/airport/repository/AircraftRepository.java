package com.finalproject.airport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finalproject.airport.model.Aircraft;

@Repository
public interface AircraftRepository extends JpaRepository<Aircraft, String> {

    List<Aircraft> findByPassengerCapacityGreaterThanEqual(int passengerCapacity);
}