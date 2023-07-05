package com.tinqin.academy.bff.business.converters;

import com.tinqin.academy.bff.api.operations.entityoutputmodels.CommentBffOutput;
import entityoutputmodels.CommentOutput;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class CommentOutputToCommentBffOutputConverter implements Converter<CommentOutput, CommentBffOutput> {

    @Override
    public CommentBffOutput convert(CommentOutput source) {
        return CommentBffOutput.builder()
                .id(source.getId())
                .userId(source.getUserId())
                .comment(source.getComment())
                .build();
    }
}
