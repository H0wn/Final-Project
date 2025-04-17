package com.finalproject.airport.service.utilities;

import java.util.Map;

import com.finalproject.airport.model.Flight;

public class Utilities {
    public static final Map<Long, String> MONTH_MAP = Map.ofEntries(
        Map.entry(1L, "GEN"),
        Map.entry(2L, "FEB"),
        Map.entry(3L, "MAR"),
        Map.entry(4L, "APR"),
        Map.entry(5L, "MAG"),
        Map.entry(6L, "GIU"),
        Map.entry(7L, "LUG"),
        Map.entry(8L, "AGO"),
        Map.entry(9L, "SET"),
        Map.entry(10L, "OTT"),
        Map.entry(11L, "NOV"),
        Map.entry(12L, "DIC")
    );

/**
 * Cleans the date and time strings in a Flight object by removing the 
 * fractional seconds part from the departure and arrival times.
 *
 * @param flight the Flight object with departure and arrival times containing fractional seconds
 * @return the Flight object with cleaned departure and arrival times
 */

    static public Flight cleanDates(Flight flight) {
        String[] departureTime = flight.getDepartureTime().split(",");
        String[] arrivalTime = flight.getArrivalTime().split(",");

        flight.setDepartureTime(departureTime[0]);
        flight.setArrivalTime(arrivalTime[0]);
    
        return flight;
    }
}
