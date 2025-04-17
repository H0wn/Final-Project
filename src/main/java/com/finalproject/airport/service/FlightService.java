package com.finalproject.airport.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import com.finalproject.airport.model.Airport;
import com.finalproject.airport.model.Flight;
import com.finalproject.airport.repository.AirportRepository;
import com.finalproject.airport.repository.FlightRepository;
import com.finalproject.airport.service.utilities.Utilities;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final AirportRepository airportRepository;
    
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }
    
    /**
     * Retrieves all flights that match the given search criteria.
     * @param departureCity the city of departure
     * @param arrivalCity the city of arrival
     * @param day the day of departure in the format "yyyy-MM-dd"
     * @param baggageWeight the weight of the baggage
     * @return a list of flights that match the search criteria, with the day and time of departure and arrival formatted as "dd MMM yyyy HH:mm"
     */
   public List<Flight> searchFlights(
    String departureCity,
    String arrivalCity,
    String day,
    Integer baggageWeight) {


    DateTimeFormatter dbFormatter = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendPattern("dd-")
    .appendText(ChronoField.MONTH_OF_YEAR, Utilities.MONTH_MAP)
    .appendPattern("-yy")
    .toFormatter(Locale.ITALIAN);
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    LocalDate searchDate = LocalDate.parse(day, inputFormatter);

    List<Flight> flights = flightRepository.findByDepartureCityAndArrivalCity(departureCity, arrivalCity);

    flights = flights.stream()
        .filter(f -> {
                LocalDate flightDate = LocalDate.parse(f.getDay(), dbFormatter);
                return !flightDate.isBefore(searchDate);
        })
        .filter(f -> f.getPassengers() < f.getAircraft().getPassengerCapacity())
        .filter(f -> (f.getCargo() + baggageWeight) <= f.getAircraft().getCargoCapacity())
        .map(Utilities::cleanDates)
        .collect(Collectors.toList());

        return flights;
    }
}