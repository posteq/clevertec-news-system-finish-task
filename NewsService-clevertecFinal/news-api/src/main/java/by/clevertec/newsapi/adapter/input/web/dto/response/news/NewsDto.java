package by.clevertec.newsapi.adapter.input.web.dto.response.news;

import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;

import java.time.LocalDateTime;

/**
 * DTO for {@link NewsEntity}
 */

public record NewsDto(Long id, String title, String text,
                      LocalDateTime dateOfCreation) {
}
