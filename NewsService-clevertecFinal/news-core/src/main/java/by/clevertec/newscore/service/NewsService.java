package by.clevertec.newscore.service;


import by.clevertec.newscore.cache.annotation.CacheDelete;
import by.clevertec.newscore.cache.annotation.CacheUpdate;
import by.clevertec.newscore.cache.annotation.Cacheable;
import by.clevertec.newscore.domain.comment.Comment;
import by.clevertec.newscore.domain.news.CreatedNews;
import by.clevertec.newscore.domain.news.News;
import by.clevertec.newscore.port.input.NewsUseCase;
import by.clevertec.newscore.port.output.NewsPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис работы с новостями
 * @see NewsUseCase
 */

@Service
@RequiredArgsConstructor
public class NewsService implements NewsUseCase {

    private final NewsPersistencePort newsPersistencePort;

    @Cacheable
    @Override
    public News readById(Long id) {
        return newsPersistencePort.readNewsById(id);
    }

    @CacheDelete
    @Override
    public void delete(Long id) {
        newsPersistencePort.deleteCommentsNews(id);
        newsPersistencePort.delete(id);
    }

    @CacheUpdate
    @Override
    public News update(Long id, CreatedNews newsDomain) {
        News news = newsPersistencePort.readNewsById(id);
        if(newsDomain.getTitle()!= null) {
            news.setTitle(newsDomain.getTitle());
        }
        if(newsDomain.getText()!= null) {
            news.setText(newsDomain.getText());
        }
        return newsPersistencePort.create(news);
    }

    @CachePut
    @Override
    public News create(CreatedNews newsDomain) {
        News news = new News();
        news.setTitle(newsDomain.getTitle());
        news.setText(newsDomain.getText());
        return newsPersistencePort.create(news);
    }

    @Override
    public List<News> readAll() {
        return newsPersistencePort.readAllNews();
    }

    @Override
    public List<News> readAllByPage(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return newsPersistencePort.readAllNews(pageRequest);
    }

    @Override
    public List<News> readByQuery(String query, int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return newsPersistencePort.readByQuery(query,pageRequest);
    }

    @Override
    public News readByIdWithComments(Long newsId, int pageNumber, int pageSize) {
        News news = newsPersistencePort.readNewsById(newsId);
        List<Comment> comments = newsPersistencePort.getCommentsByNewsIs(newsId,pageNumber, pageSize);
        news.setComments(comments);
        return news;
    }

    @Override
    public Comment getNewsComment(Long newsId, Long commentId){
        newsPersistencePort.readNewsById(newsId);
        return newsPersistencePort.findCommentForNews(commentId);
    }


}
