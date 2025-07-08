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
    @NotNull(message = "ID рейса не может быть быть пустым")
    private Long flightId;

    @NotBlank(message = "Имя пассажира не может быть пустым")
    private String passengerName;

    @Email(message = "Email не соответствует формату")
    @NotBlank(message = "Email не может быть пустым")
    private String passengerEmail;

    @Min(value = 1, message = "Должно быть выбрано хотя бы одно место")
    private Integer seats;
}