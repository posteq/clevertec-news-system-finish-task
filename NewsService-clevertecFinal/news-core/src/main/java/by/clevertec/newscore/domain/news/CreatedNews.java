package by.clevertec.newscore.domain.news;

import lombok.Getter;
import lombok.Setter;

/**
 * Доменная модель создания и обновления новости {@link News}
 *
 * @field title
 * @field text
 */
@Getter
@Setter
public class CreatedNews {
    private String title;
    private String text;
}
