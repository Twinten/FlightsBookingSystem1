
package com.ex.flightbooking.controller;

import com.ex.flightbooking.config.BusinessLogicException;
import com.ex.flightbooking.config.ResourceNotFoundException;
import com.ex.flightbooking.dto.BookingRequest;
import com.ex.flightbooking.model.Booking;
import com.ex.flightbooking.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public ResponseEntity<Booking> createBooking(@Valid @RequestBody BookingRequest request) {
        try {
            Booking booking = bookingService.createBooking(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(booking);
        } catch (BusinessLogicException ex) {
            throw new BusinessLogicException("Ошибка при создании бронирования: " + ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getUserBookings(@RequestParam String email) {
        List<Booking> bookings = bookingService.getUserBookings(email);
        if (bookings.isEmpty()) {
            throw new ResourceNotFoundException("Бронирования для email " + email + " не найдены");
        }
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException ex) {
            throw new ResourceNotFoundException("Бронирование с ID " + id + " не найдено");
        } catch (BusinessLogicException ex) {
            throw new BusinessLogicException("Ошибка при отмене бронирования: " + ex.getMessage());
        }
    }
}