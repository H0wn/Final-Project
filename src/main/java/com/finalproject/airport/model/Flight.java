package com.finalproject.airport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "FLIGHTS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer flightId;

    @Column(name = "DAY")
    private String day;
    
    @Column(name = "DEPARTURE_CITY")
    private String departureCity;
    
    @Column(name = "DEPARTURE_TIME")
    private String departureTime;
    
    @Column(name = "ARRIVAL_CITY")
    private String arrivalCity;
    
    @Column(name = "ARRIVAL_TIME")
    private String arrivalTime;
    
    @ManyToOne
    @JoinColumn(name = "AIRCRAFT_TYPE")
    private Aircraft aircraft;
    
    @Column(name = "PASSENGERS")
    private Integer passengers;

    @Column(name = "CARGO")
    private Integer cargo;

    
    public Flight(
        String day,
        String departureCity,
        String departureTime,
        String arrivalCity,
        String arrivalTime,
        Aircraft aircraft,
        Integer passengers,
        Integer cargo
    ) {
        this.day = day;
        this.departureCity = departureCity;
        this.departureTime = departureTime;
        this.arrivalCity = arrivalCity;
        this.arrivalTime = arrivalTime;
        this.aircraft = aircraft;
        this.passengers = passengers;
        this.cargo = cargo;
    }
}