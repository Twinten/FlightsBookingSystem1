package com.ex.flightbooking.repository;

import com.ex.flightbooking.model.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {
    List<Flight> findByDepartureAirportAndArrivalAirportAndDepartureTimeBetween(
        String departureAirport,
        String arrivalAirport,
        LocalDateTime departureStart,
        LocalDateTime departureEnd
    );
}
