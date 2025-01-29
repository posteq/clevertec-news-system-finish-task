package by.clevertec.commentcore.port.output;

import by.clevertec.commentcore.domain.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс общения с репозиторием
 */
public interface CommentPersistencePort {

    /**
     * Поиск комментария по id
     * @param id
     * @return Comment
     */
    Comment readById(Long id);

    /**
     * Поиск всех комментариев с пагинацией
     * @param pageable
     * @return List
     */
    List<Comment> readAllByPage(Pageable pageable);

    /**
     * Удаление комментария по id
     * @param id
     */
    void delete(Long id);

    /**
     * Создание комментария
     * @param comment
     */
    Comment create(Comment comment);

    /**
     * Поиск комментариев по id новости с пагинацией
     * @param id
     * @param page
     * @return List
     */
    List<Comment> readCommentByNewsId(Long id, Pageable page);

    /**
     * Удаление комментариев по id новости
     * @param newsId
     */
    void deleteAllByNews(Long newsId);
}
