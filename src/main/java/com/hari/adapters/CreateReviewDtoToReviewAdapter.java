package com.hari.adapters;

import com.hari.dto.CreateReviewDto;
import com.hari.model.Review;


public interface CreateReviewDtoToReviewAdapter {
    public Review convertDto(CreateReviewDto createReviewDto);
}
