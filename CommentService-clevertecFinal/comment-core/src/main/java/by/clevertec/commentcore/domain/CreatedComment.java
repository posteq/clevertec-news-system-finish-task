package by.clevertec.commentcore.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * Доменная модель создания и обновления комментария
 * @see Comment
 *
 * @field text
 * @field username
 * @field newsId
 */

@Getter
@Setter
public class CreatedComment {
    private String text;
    private String username;
    private Long newsId;
}
