package by.clevertec.newscore.port.input;

import by.clevertec.newscore.domain.comment.Comment;
import by.clevertec.newscore.domain.news.CreatedNews;
import by.clevertec.newscore.domain.news.News;

import java.util.List;
/**
 * Входной интерфейс общения с сервисом
 */
public interface NewsUseCase {

    /**
     * Поиск новости по id
     * @param id
     * @return News
     */
    News readById(Long id);

    /**
     * Поиск всех новостей без пагинации
     * @return List
     */
    List<News> readAll();

    /**
     * Удаление новости по id
     * @param id
     */
    void delete(Long id);

    /**
     * Создание новости
     * @param newsDomain
     * @return News
     */
    News create(CreatedNews newsDomain);

    /**
     * Обновление новости
     * @param id
     * @param newsDomain
     * @return News
     */
    News update(Long id, CreatedNews newsDomain);

    /**
     * Поиск всех новостей с пагинацией
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    List<News> readAllByPage(int pageNumber, int pageSize);

    /**
     * Поиск по названию или тексту новости с пагинацией, не выполняет поиск если искомая фраза содержит стоп-слово(предлог)
     * @param query
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    List<News> readByQuery(String query, int pageNumber, int pageSize);

    /**
     * Поиск новости с комментариями, с пагинацией
     * @param newsId
     * @param pageNumber
     * @param pageSize
     * @return News
     */
    News readByIdWithComments(Long newsId, int pageNumber, int pageSize);

    /**
     * Поиск комментария для новости
     * @param newsId
     * @param commentId
     * @return Comment
     */
    Comment getNewsComment(Long newsId, Long commentId);
}
