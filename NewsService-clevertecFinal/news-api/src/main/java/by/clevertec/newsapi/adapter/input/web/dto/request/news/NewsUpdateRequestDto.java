package by.clevertec.newsapi.adapter.input.web.dto.request.news;

import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;

/**
 * DTO for {@link NewsEntity}
 */
public record NewsUpdateRequestDto(String title,
                                   String text) {
}
