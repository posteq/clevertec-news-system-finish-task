package by.clevertec.commentapi.adapter.output.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Сущность комментария
 *
 * @field id
 * @field username
 * @field username
 * @field newsId;
 * @field dateOfCreation
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_of_creation")
    @CreationTimestamp
    private LocalDateTime dateOfCreation;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "username")
    private String username;

    @Column(name = "news_id")
    private Long newsId;

}