package by.clevertec.newsapi.mapper;

import by.clevertec.newsapi.adapter.input.web.dto.response.comment.CommentResponse;
import by.clevertec.newscore.domain.comment.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    Comment toDomain(CommentResponse commentResponse);
    List<Comment> toDomain(List<CommentResponse> commentResponses);

    CommentResponse toDto(Comment newsComment);
}
