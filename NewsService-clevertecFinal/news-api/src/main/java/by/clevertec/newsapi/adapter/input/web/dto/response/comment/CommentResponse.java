package by.clevertec.newsapi.adapter.input.web.dto.response.comment;

import java.time.LocalDateTime;

public record CommentResponse(Long id, LocalDateTime dateOfCreation, String text,
                              String username) {
}
