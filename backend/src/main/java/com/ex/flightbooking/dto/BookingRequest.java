package com.ex.flightbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    @NotNull(message = "Flight ID cannot be null")
    private Long flightId;

    @NotBlank(message = "Passenger name cannot be blank")
    private String passengerName;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email cannot be blank")
    private String passengerEmail;

    @Min(value = 1, message = "At least 1 seat must be booked")
    private Integer seats;
}