package by.clevertec.newsapi.adapter.input.web.dto.request.news;

import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;
import jakarta.validation.constraints.NotBlank;
/**
 * DTO for {@link NewsEntity}
 */
public record NewsNewRequestDto(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Text is required")
        String text) {}
