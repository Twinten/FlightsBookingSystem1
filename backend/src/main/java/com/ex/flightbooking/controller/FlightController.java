package com.ex.flightbooking.controller;

import com.ex.flightbooking.dto.FlightDTO;
import com.ex.flightbooking.model.Flight;
import com.ex.flightbooking.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/flights")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping("/search")
    public ResponseEntity<List<FlightDTO>> searchFlights(
        @RequestParam String from,
        @RequestParam String to,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        List<Flight> flights = flightService.searchFlights(from, to, date);
        List<FlightDTO> dtos = flights.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FlightDTO> getFlight(@PathVariable Long id) {
        Flight flight = flightService.getFlightById(id);
        return ResponseEntity.ok(convertToDTO(flight));
    }

    private FlightDTO convertToDTO(Flight flight) {
        return FlightDTO.builder()
            .id(flight.getId())
            .flightNumber(flight.getFlightNumber())
            .departureAirport(flight.getDepartureAirport())
            .arrivalAirport(flight.getArrivalAirport())
            .departureTime(flight.getDepartureTime())
            .arrivalTime(flight.getArrivalTime())
            .price(flight.getPrice())
            .totalSeats(flight.getTotalSeats())
            .availableSeats(flight.getAvailableSeats())
            .build();
    }
}