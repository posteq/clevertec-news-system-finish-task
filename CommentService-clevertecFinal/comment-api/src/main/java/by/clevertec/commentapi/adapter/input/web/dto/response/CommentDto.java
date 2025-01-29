package by.clevertec.commentapi.adapter.input.web.dto.response;

import by.clevertec.commentapi.adapter.output.jpa.entity.CommentEntity;

import java.time.LocalDateTime;

/**
 * DTO for {@link CommentEntity}
 */

public record CommentDto(Long id,LocalDateTime dateOfCreation, String text,
                         String username) {
}