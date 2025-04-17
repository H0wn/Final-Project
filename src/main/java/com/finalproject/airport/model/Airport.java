package com.finalproject.airport.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "AIRPORTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer airportId;
    
    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY")
    private String country;
    
    // Numero di piste
    @Column(name = "NUMBER_OF_RUNWAYS")
    private Integer numberOfRunways;
}