package com.ex.flightbooking.service;

import com.ex.flightbooking.config.BusinessLogicException;
import com.ex.flightbooking.config.ResourceNotFoundException;
import com.ex.flightbooking.model.Flight;
import com.ex.flightbooking.repository.FlightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;

    public List<Flight> searchFlights(String from, String to, LocalDate departureDate) {
        LocalDateTime startOfDay = departureDate.atStartOfDay();
        LocalDateTime endOfDay = departureDate.atTime(LocalTime.MAX);
        return flightRepository.findByDepartureAirportAndArrivalAirportAndDepartureTimeBetween(
                from, to, startOfDay, endOfDay
        );
    }

    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight not found with id: " + id));
    }

    public Flight updateAvailableSeats(Long flightId, int seatsChange) {
        Flight flight = getFlightById(flightId);
        int newAvailableSeats = flight.getAvailableSeats() + seatsChange;

        if (newAvailableSeats < 0 || newAvailableSeats > flight.getTotalSeats()) {
            throw new BusinessLogicException("Invalid seats count. Available seats cannot be negative or exceed total seats");
        }

        flight.setAvailableSeats(newAvailableSeats);
        return flightRepository.save(flight);
    }
}