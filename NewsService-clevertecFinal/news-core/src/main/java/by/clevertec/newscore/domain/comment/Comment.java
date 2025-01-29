package by.clevertec.newscore.domain.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Доменная модель комментария
 *
 * @field id
 * @field dateOfCreation
 * @field text
 * @field username
 */
@Getter
@Setter
public class Comment {
    private Long id;
    private LocalDateTime dateOfCreation;
    private String text;
    private String username;
}
