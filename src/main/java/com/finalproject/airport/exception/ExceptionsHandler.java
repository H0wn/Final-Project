package com.finalproject.airport.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(FlightNotFoundException.class)
    public String handleFlightNotFound(FlightNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Volo non trovato");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(AircraftNotFoundException.class)
    public String handleAircraftNotFound(AircraftNotFoundException ex, Model model) {
        model.addAttribute("errorTitle", "Aereo non trovato");
        model.addAttribute("errorMessage", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericError(Exception ex, Model model) {
        model.addAttribute("errorTitle", "Errore del server");
        model.addAttribute("errorMessage", "Si è verificato un errore imprevisto. Riprova più tardi.");
        return "error/500";
    }
}
