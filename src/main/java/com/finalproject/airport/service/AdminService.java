package com.finalproject.airport.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.finalproject.airport.exception.AircraftNotFoundException;
import com.finalproject.airport.exception.FlightNotFoundException;
import com.finalproject.airport.model.Aircraft;
import com.finalproject.airport.model.Airport;
import com.finalproject.airport.model.Flight;

import com.finalproject.airport.repository.AircraftRepository;
import com.finalproject.airport.repository.AirportRepository;
import com.finalproject.airport.repository.FlightRepository;
import com.finalproject.airport.service.utilities.Utilities;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final FlightRepository flightRepository;
    private final AircraftRepository aircraftRepository;
    private final AirportRepository airportRepository;
    
    /**
     * Adds a new flight to the database.
     * 
     * @param day the day and time of the flight in ISO-8601 format (e.g., "2023-10-15T14:30")
     * @param departureCity the city from which the flight departs
     * @param arrivalCity the city where the flight arrives
     * @param aircraftType the type of aircraft used for the flight
     * @return the saved Flight entity
     */

    public Flight addFlight(String day, String departureCity, String arrivalCity, Aircraft aircraftType) {
        
        LocalDateTime dateTime = LocalDateTime.parse(day);

        String dayForDb = String.format("%02d-%s-%02d",
                dateTime.getDayOfMonth(),
                Utilities.MONTH_MAP.get((long) dateTime.getMonthValue()),
                dateTime.getYear() % 100);

        StringBuilder dateTimeBuilder= new StringBuilder(dayForDb + " ");

        String dayTime = day.split("T")[1];
        

        dateTimeBuilder.append(dayTime);
        dateTimeBuilder.append(":00,000000000");

        String dateTimeForDb = dateTimeBuilder.toString();
        return flightRepository.save(new Flight( 
            dayForDb,
            departureCity,
            dateTimeForDb,
            arrivalCity,
            dateTimeForDb,
            aircraftType,
            0,
            0
            )
        );
    }
    
    /**
     * Updates the aircraft type of an existing flight.
     * @param flightId the ID of the flight to be updated
     * @param aircraftType the new type of aircraft
     * @return the updated flight
     * @throws NoSuchElementException if the flight or aircraft is not found
     */

    public Flight updateFlightAircraft(Integer flightId, String aircraftType) {
        Flight flight = flightRepository.findById(flightId).orElseThrow(
            () -> new FlightNotFoundException("Volo con ID " + flightId + " non trovato.")
        );
        Aircraft aircraft = aircraftRepository.findById(aircraftType).orElseThrow(
            () -> new AircraftNotFoundException("Aereo con ID " + aircraftType + " non trovato.")
        );
        flight.setAircraft(aircraft);
        return flightRepository.save(flight);
    }
    
    /**
     * Deletes the flight with the given ID
     * @param flightId the ID of the flight to be deleted
     * @throws NoSuchElementException if the flight is not found
     */
    public void deleteFlight(Integer flightId) {
        flightRepository.deleteById(flightId);
    }
    

    /**
     * Retrieves all upcoming flights.
     * @return a list of upcoming flights with the day and time of departure and arrival formatted as "dd MMM yyyy HH:mm"
     */
    public List<Flight> getAllUpcomingFlights() {
        List<Flight> flights = flightRepository.findAll();
        return flights.stream()
            .map(Utilities::cleanDates)
            .toList();
    }

    /**
     * Retrieves a flight by its ID.
     * @param flightId the ID of the flight to retrieve
     * @return the flight with the specified ID
     * @throws NoSuchElementException if the flight is not found
     */

    public Flight getFlightById(Integer flightId) {
        return flightRepository.findById(flightId).orElseThrow(
            () -> new FlightNotFoundException("Volo con ID " + flightId + " non trovato.")
        );
    }

    /**
     * Retrieves all aircrafts stored in the database.
     * @return a list of all aircrafts
     */
    public List<Aircraft> getAllAircrafts() {
        List<Aircraft> aircrafts = aircraftRepository.findAll();
        return aircrafts;
    }

    /**
     * Retrieves all aircrafts stored in the database that have a passenger capacity greater or equal
     * than the passenger capacity of the given flight's aircraft.
     * @param flight the flight to compare aircrafts with
     * @return a list of all aircrafts with a passenger capacity greater or equal than the given flight's aircraft
     */
    public List<Aircraft> getAllAircraftsWithEnoughPassengerCapacity(Flight flight) {
        Aircraft currentAircraft = flight.getAircraft();
        return aircraftRepository.findByPassengerCapacityGreaterThanEqual(currentAircraft.getPassengerCapacity());
    }

    /**
     * Retrieves all airports stored in the database.
     * @return a list of all airports
     */
    public List<Airport> getAllAirports() {
        return airportRepository.findAll();
    }
}