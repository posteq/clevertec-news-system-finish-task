package by.clevertec.commentapi.adapter.input.web.dto.request;

import by.clevertec.commentapi.adapter.output.jpa.entity.CommentEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for {@link CommentEntity}
 */
public record CommentNewRequestDto(
        @NotBlank(message = "Text is required")
        String text,
        @NotBlank(message = "Username is required")
        String username,
        @NotNull(message = "NewsId is required")
        Long newsId) {}
