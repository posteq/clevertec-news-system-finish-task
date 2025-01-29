package by.clevertec.newscore.domain.news;

import by.clevertec.newscore.domain.comment.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Доменная модель новости с комментариями
 *
 * @field id
 * @field title
 * @field text
 * @field dateOfCreation
 * @field List<Comment>
 */

@Getter
@Setter
public class NewsWithComments {
    private Long id;
    private LocalDateTime dateOfCreation;
    private String title;
    private String text;
    private List<Comment> comments;
}
