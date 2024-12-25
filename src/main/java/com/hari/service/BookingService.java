package com.hari.service;

import com.hari.dto.CreateBookingDto;
import com.hari.request.UpdateBookingRequestDto;
import com.hari.response.CreateBookingResponseDto;
import com.hari.response.UpdateBookingResponseDto;


public interface BookingService {
    CreateBookingResponseDto createBooking(CreateBookingDto bookingDetails);
    UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto bookingRequestDto, Long bookingId);
}
