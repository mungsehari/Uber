package com.hari.response;

import com.hari.model.Driver;
import com.hari.status.BookingStatus;
import lombok.*;

import java.util.Optional;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookingResponseDto {
    private Long bookingId;
    private BookingStatus status;
    private Optional<Driver> driver;

}
