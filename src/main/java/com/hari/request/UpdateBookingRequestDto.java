package com.hari.request;

import lombok.*;

import java.util.Optional;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookingRequestDto {
    private String status;
    private Optional<Long> driverId;
}
