package by.clevertec.commentapi.mapper;

import by.clevertec.commentapi.adapter.input.web.dto.request.CommentNewRequestDto;
import by.clevertec.commentapi.adapter.input.web.dto.request.CommentUpdateRequestDto;
import by.clevertec.commentapi.adapter.input.web.dto.response.CommentDto;
import by.clevertec.commentapi.adapter.output.jpa.entity.CommentEntity;
import by.clevertec.commentcore.domain.Comment;
import by.clevertec.commentcore.domain.CreatedComment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {

    Comment toDomain(CommentEntity entity);

    List<Comment> toDomain(List<CommentEntity> entity);

    CommentEntity toEntity(Comment domain);

    CommentDto toDto(Comment domain);

    List<CommentDto> toDto(List<Comment> domain);

    CreatedComment toNewDomain(CommentNewRequestDto dto);
    CreatedComment toUpdateDomain(CommentUpdateRequestDto dto);

}
