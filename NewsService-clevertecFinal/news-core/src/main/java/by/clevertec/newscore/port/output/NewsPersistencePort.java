package by.clevertec.newscore.port.output;

import by.clevertec.newscore.domain.comment.Comment;
import by.clevertec.newscore.domain.news.News;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс общения с репозиторием
 */
public interface NewsPersistencePort {

    /**
     * Поиск новости по id
     * @param id
     * @return News
     */
   News readNewsById(Long id);

    /**
     * Поиск всех новостей без пагинации
     * @return List
     */
    List<News> readAllNews();

    /**
     * Поиск всех новостей с пагинацией
     * @param pageable
     * @return List
     */
    List<News> readAllNews(Pageable pageable);

    /**
     * Удаление новости по id
     * @param id
     */
    void delete(Long id);

    /**
     * Создание новости
     * @param news
     */
    News create(News news);

    /**
     * Поиск по названию или тексту новостей с пагинацией
     * @param query
     * @param pageRequest
     * @return List
     */
    List<News> readByQuery(String query, Pageable pageRequest);

    /**
     * Поиск комментариев по id новости
     * @param newsId
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    List<Comment> getCommentsByNewsIs(Long newsId, int pageNumber, int pageSize);

    /**
     * Поиск комментария по id
     * @param commentId
     * @return Comment
     */
    Comment findCommentForNews(Long commentId);
    /**
     * Удаление комментария по id
     * @param newsId
     */
    void deleteCommentsNews(Long newsId);
}
