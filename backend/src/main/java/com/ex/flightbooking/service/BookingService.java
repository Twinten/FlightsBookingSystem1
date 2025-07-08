
package com.ex.flightbooking.service;

import com.ex.flightbooking.config.BusinessLogicException;
import com.ex.flightbooking.config.ResourceNotFoundException;
import com.ex.flightbooking.dto.BookingRequest;
import com.ex.flightbooking.model.Booking;
import com.ex.flightbooking.model.BookingStatus;
import com.ex.flightbooking.model.Flight;
import com.ex.flightbooking.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final BookingRepository bookingRepository;
    private final FlightService flightService;

    public Booking createBooking(BookingRequest request) {
        Flight flight = flightService.getFlightById(request.getFlightId());

        if (flight.getAvailableSeats() < request.getSeats()) {
            throw new BusinessLogicException("Не достаточно свободных мест");
        }

        Booking booking = Booking.builder()
                .flight(flight)
                .passengerName(request.getPassengerName())
                .passengerEmail(request.getPassengerEmail())
                .seatsBooked(request.getSeats())
                .bookingDate(LocalDateTime.now())
                .status(BookingStatus.CONFIRMED)
                .build();

        flightService.updateAvailableSeats(flight.getId(), -request.getSeats());

        return bookingRepository.save(booking);
    }

    public List<Booking> getUserBookings(String email) {
        return bookingRepository.findByPassengerEmail(email);
    }

    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Бронирование с данным ID не найдено: " + bookingId));

        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new BusinessLogicException("Только подтвержденное бронирование может быть отменено");
        }

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);

        flightService.updateAvailableSeats(booking.getFlight().getId(), booking.getSeatsBooked());
    }
}