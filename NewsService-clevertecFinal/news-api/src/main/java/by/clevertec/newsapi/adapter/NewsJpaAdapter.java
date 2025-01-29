package by.clevertec.newsapi.adapter;

import by.clevertec.logger.annotation.LogIt;
import by.clevertec.newsapi.adapter.input.web.dto.response.comment.CommentResponse;
import by.clevertec.newsapi.adapter.output.client.CommentsClient;
import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;
import by.clevertec.newsapi.adapter.output.jpa.repository.NewsRepository;
import by.clevertec.newsapi.exception.EntityFindException;
import by.clevertec.newsapi.mapper.CommentMapper;
import by.clevertec.newsapi.mapper.NewsMapper;
import by.clevertec.newscore.domain.comment.Comment;
import by.clevertec.newscore.domain.news.News;
import by.clevertec.newscore.port.output.NewsPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Адаптер работы с репозиторием, реализует посредством JPA
 * @see NewsPersistencePort
 */
@Component
@RequiredArgsConstructor
public class NewsJpaAdapter implements NewsPersistencePort {

    private final NewsRepository repository;
    private final NewsMapper newsMapper;
    private final CommentsClient commentsClient;
    private final CommentMapper commentMapper;

    @LogIt
    @Override
    public News readNewsById(Long id) {
        return newsMapper.toDomain(repository.findById(id)
                .orElseThrow(() -> new EntityFindException(id)));
    }

    @Override
    public List<News> readAllNews(Pageable pageRequest) {
        return newsMapper.toDomain(repository.findAll(pageRequest)
                                                .getContent());
    }

    @Override
    public List<News> readAllNews() {
        return newsMapper.toDomain(repository.findAll());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public News create(News news) {
        NewsEntity newsEntity = newsMapper.toEntity(news);
        return newsMapper.toDomain(repository.save(newsEntity));
    }

    @Override
    public List<News> readByQuery(String query, Pageable pageRequest) {
        return newsMapper.toDomain(repository.findByQuery(query,pageRequest)
                .getContent());
    }

    @Override
    public List<Comment> getCommentsByNewsIs(Long newsId, int pageNumber, int pageSize) {
        List<CommentResponse> commentsByNews = commentsClient.getCommentsByNews(newsId, pageNumber, pageSize);
        return commentMapper.toDomain(commentsByNews);
    }

    @Override
    public Comment findCommentForNews(Long commentId) {
        return commentMapper.toDomain(commentsClient.getComment(commentId));
    }

    @Override
    public void deleteCommentsNews(Long newsId) {
        commentsClient.deleteCommentsByNewsId(newsId);
    }

}
