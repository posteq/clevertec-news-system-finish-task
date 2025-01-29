package by.clevertec.commentapi.adapter.input.web.dto.request;

import by.clevertec.commentapi.adapter.output.jpa.entity.CommentEntity;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO for {@link CommentEntity}
 */
public record CommentUpdateRequestDto(
        @NotBlank(message = "Text is required")
        String text) {
}
