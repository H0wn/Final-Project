package com.finalproject.airport.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.finalproject.airport.service.FlightService;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    /**
     * Handles the request to display the home page.
     * Retrieves all airports stored in the database using the flight service
     * and adds them to the model to be displayed in the view.
     * 
     * @param model the model to pass to the view
     * @return the view name for the home page
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("airports", flightService.getAllAirports());
        return "home";
    }

    /**
     * Handles the request to search for flights.
     * Retrieves all flights that match the given search criteria using the flight service
     * and adds them to the model to be displayed in the view.
     * 
     * @param departureCity the city of departure
     * @param arrivalCity the city of arrival
     * @param day the day of departure in the format "yyyy-MM-dd"
     * @param baggageWeight the weight of the baggage
     * @param model the model to pass to the view
     * @return the view name for the flight list page
     */
    @PostMapping("/search")
    public String searchFlights(
        @RequestParam String departureCity,
        @RequestParam String arrivalCity,
        @RequestParam String day,
        @RequestParam int baggageWeight,
        Model model) {
        
        model.addAttribute("flights", 
            flightService.searchFlights(departureCity, arrivalCity, day, baggageWeight));
        return "flight-list";
    }
}