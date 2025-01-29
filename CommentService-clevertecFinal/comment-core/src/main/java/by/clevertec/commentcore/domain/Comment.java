package by.clevertec.commentcore.domain;

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
 * @field newsId
 */

@Getter
@Setter
public class Comment {
    private Long id;
    private LocalDateTime dateOfCreation;
    private String text;
    private String username;
    private Long newsId;
}
