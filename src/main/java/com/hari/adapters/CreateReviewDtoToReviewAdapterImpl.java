package com.hari.adapters;

import com.hari.dto.CreateReviewDto;
import com.hari.model.Booking;
import com.hari.model.Review;
import com.hari.repository.BookingRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CreateReviewDtoToReviewAdapterImpl  implements CreateReviewDtoToReviewAdapter {
    private final BookingRepository bookingRepository;

    public CreateReviewDtoToReviewAdapterImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public Review convertDto(CreateReviewDto createReviewDto) {
        Optional<Booking> booking = bookingRepository.findById(createReviewDto.getBookingId());
        return booking.map(value -> Review.builder()
                .rating(createReviewDto.getRating())
                .booking(value)
                .content(createReviewDto.getContent())
                .build()).orElse(null);
    }

}
