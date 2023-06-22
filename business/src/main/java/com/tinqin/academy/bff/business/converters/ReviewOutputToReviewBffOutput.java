package com.tinqin.academy.bff.business.converters;

import com.tinqin.academy.bff.api.simpleGameSearch.entityoutputmodels.ReviewBffOutput;
import com.tinqin.academy.bff.api.simpleGameSearch.entityoutputmodels.UserBffOutput;
import com.tinqin.academy.piim.api.entityoutputmodels.ReviewOutput;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ReviewOutputToReviewBffOutput implements Converter<ReviewOutput, ReviewBffOutput> {

    @Override
    public ReviewBffOutput convert(ReviewOutput source) {
        return ReviewBffOutput.builder()
                .id(source.getId())
                .score(source.getScore())
                .text(source.getText())
                .user(UserBffOutput.builder()
                        .id(source.getUser().getId())
                        .fullName(source.getUser().getFullName())
                        .build())
                .build();
    }
}
