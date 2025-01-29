package by.clevertec.newsapi.adapter.input.web.dto.response.news;

import by.clevertec.newsapi.adapter.input.web.dto.response.comment.CommentResponse;
import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link NewsEntity} с комментариями
 */

public record NewsDtoWithComments (Long id, String title, String text,
                                   LocalDateTime dateOfCreation, List<CommentResponse> comments) {
}
