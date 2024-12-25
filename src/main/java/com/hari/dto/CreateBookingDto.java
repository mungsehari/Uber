package com.hari.dto;

import com.hari.model.ExactLocation;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateBookingDto {
    private Long passengerId;

    private ExactLocation startLocation;

    private ExactLocation endLocation;
}
