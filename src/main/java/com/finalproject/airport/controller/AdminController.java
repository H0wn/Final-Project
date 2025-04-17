package com.finalproject.airport.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import com.finalproject.airport.model.Aircraft;
import com.finalproject.airport.model.Airport;
import com.finalproject.airport.model.Flight;
import com.finalproject.airport.service.AdminService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    
    /**
     * Handles the login form request. If the user is already authenticated, then
     * redirects to the dashboard. Otherwise, renders the login form.
     * 
     * @return the login form page
     */
    @GetMapping("/login")
    public String loginForm() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && 
            !(auth.getPrincipal() instanceof String && auth.getPrincipal().equals("anonymousUser"))) {
            return "redirect:/admin/dashboard";
        }

        return "admin/admin-login";   
    }
    /**
     * Handles the dashboard page request. Retrieves all upcoming flights and
     * passes them to the view.
     * 
     * @param model the model to pass to the view
     * @return the view name
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("flights", adminService.getAllUpcomingFlights());
        return "admin/admin-dashboard";
    }
    
    /**
     * Handles the request to list all upcoming flights.
     * Retrieves all upcoming flights from the admin service
     * and adds them to the model to be displayed in the view.
     * 
     * @param model the model to pass to the view
     * @return the view name for listing flights
     */

    @GetMapping("/list")
    public String listFlights(Model model) {
        model.addAttribute("flights", adminService.getAllUpcomingFlights());
        return "admin/admin-list-flights";  
    }

    /**
     * Handles the request to display the form for adding a new flight.
     * Retrieves the list of all aircrafts and airports from the admin service
     * and adds them to the model to be displayed in the view.
     * 
     * @param model the model to pass to the view
     * @return the view name for adding a flight
     */
    @GetMapping("/add")
    public String showAddFlightForm(Model model) {

        List<Aircraft> aircrafts = adminService.getAllAircrafts();
        List<Airport> airports = adminService.getAllAirports();

        model.addAttribute("airports", airports);
        model.addAttribute("aircrafts", aircrafts);
        return "admin/admin-add-flight";  
    }

    /**
     * Handles the request to add a new flight.
     * Retrieves the day, departure city, arrival city and aircraft type from the form
     * and adds a new flight to the database using the admin service.
     * Redirects to the list of all upcoming flights after adding the new flight.
     * 
     * @param day the day of the flight
     * @param departureCity the departure city of the flight
     * @param arrivalCity the arrival city of the flight
     * @param aircraftType the aircraft type of the flight
     * @return the view name for listing flights
     */
    @PostMapping("/add")
    public String addFlight( 
        @RequestParam String day,
        @RequestParam String departureCity,
        @RequestParam String arrivalCity,
        @RequestParam Aircraft aircraftType
        ) {
        adminService.addFlight(day, departureCity, arrivalCity, aircraftType);
        return "redirect:/admin/list"; 
    }

    /**
     * Retrieves the list of all aircrafts that have a passenger capacity greater or equal
     * than the passenger capacity of the given flight's aircraft and adds them to the
     * model to be displayed in the view.
     * 
     * @param flightId the id of the flight to modify
     * @param model the model to pass to the view
     * @return the view name for modifying a flight
     */
    @GetMapping("/modify/{flightId}")
    public String showModifyFlightForm(@PathVariable Integer flightId, Model model) {
        Flight flight = adminService.getFlightById(flightId);
        List<Aircraft> aircrafts = adminService.getAllAircraftsWithEnoughPassengerCapacity(flight);

        model.addAttribute("aircrafts", aircrafts);
        model.addAttribute("flight", flight);
        return "admin/admin-modify-flight";  
    }

    /**
     * Modifies the aircraft type of the flight with the given id using the admin service
     * and redirects to the list of all upcoming flights after modifying the flight.
     * 
     * @param flightId the id of the flight to modify
     * @param aircraftType the new aircraft type of the flight
     * @return the view name for listing flights
     */
    @PostMapping("/modify/{flightId}")
    public String modifyFlight(@PathVariable Integer flightId,  @RequestParam("aircraftType") String aircraftType) {
        adminService.updateFlightAircraft(flightId, aircraftType);
        return "redirect:/admin/list"; 
    }

    /**
     * Deletes the flight with the given id using the admin service
     * and redirects to the list of all upcoming flights after deleting the flight.
     * 
     * @param flightId the id of the flight to delete
     * @return the view name for listing flights
     */
    @GetMapping("/delete/{flightId}")
    public String deleteFlight(@PathVariable Integer flightId) {
        adminService.deleteFlight(flightId);
        return "redirect:/admin/list"; 
    }
}