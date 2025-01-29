package by.clevertec.commentcore.port.input;

import by.clevertec.commentcore.domain.Comment;
import by.clevertec.commentcore.domain.CreatedComment;

import java.util.List;

/**
 * Входной интерфейс общения с сервисом
 */
public interface CommentUseCase {

    /**
     * Поиск комментария по id
     * @param id
     * @return Comment
     */
    Comment readById(Long id);

    /**
     * Поиск всех комментариев с пагинацией
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    List<Comment> readAllByPage(int pageNumber, int pageSize);

    /**
     * Удаление комментария по id
     * @param id
     */
    void delete(Long id);

    /**
     * Создание комментария
     * @param comment
     * @return Comment
     */
    Comment create(CreatedComment comment);

    /**
     * Обновление комментария
     * @param id
     * @param comment
     * @return Comment
     */
    Comment update(Long id, CreatedComment comment);

    /**
     * Поиск комментариев по id новости с пагинацией
     * @param newsId
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    List<Comment> readCommentsByNewsId(Long newsId , int pageNumber, int pageSize);

    /**
     * Удаление комментариев по id новости
     * @param newsId
     */
    void deleteAllByNews(Long newsId);
}
