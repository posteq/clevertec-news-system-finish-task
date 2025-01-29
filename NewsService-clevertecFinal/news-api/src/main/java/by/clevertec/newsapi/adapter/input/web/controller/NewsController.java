package by.clevertec.newsapi.adapter.input.web.controller;

import by.clevertec.newsapi.adapter.input.web.dto.request.news.NewsNewRequestDto;
import by.clevertec.newsapi.adapter.input.web.dto.request.news.NewsUpdateRequestDto;
import by.clevertec.newsapi.adapter.input.web.dto.response.news.NewsDto;
import by.clevertec.newsapi.adapter.input.web.dto.response.news.NewsDtoWithComments;
import by.clevertec.newsapi.adapter.input.web.dto.response.comment.CommentResponse;
import by.clevertec.newsapi.mapper.CommentMapper;
import by.clevertec.newsapi.mapper.NewsMapper;
import by.clevertec.newscore.domain.comment.Comment;
import by.clevertec.newscore.domain.news.News;
import by.clevertec.newscore.port.input.NewsUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
/**
 * Контроллер работы с новостями
 */
@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
@Transactional
public class NewsController {

    private final NewsUseCase newsService;
    private final NewsMapper mapperNews;
    private final CommentMapper commentMapper;

    /**
     * Получение новости по id
     * @param newsId
     * @return NewsDto
     */
    @GetMapping("/{newsId}")
    public ResponseEntity<NewsDto> getById(@PathVariable @NotNull Long newsId) {
        News news = newsService.readById(newsId);
        return ResponseEntity.ok()
                .body(mapperNews.toDto(news));
    }

    /**
     * Получение новостей с пагинацией
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    @GetMapping
    public ResponseEntity<List<NewsDto>> getAllByPage(@RequestParam(defaultValue = "0") int pageNumber,
                                                    @RequestParam(defaultValue = "10") int pageSize) {
        List<News> news = newsService.readAllByPage(pageNumber, pageSize);

        return ResponseEntity.ok()
                .body(mapperNews.toDto(news));
    }

    /**
     * Удаление новости по id
     * @param newsId
     */
    @DeleteMapping("/{newsId}")
    public ResponseEntity<Void> deleteById(@PathVariable @NotNull Long newsId) {
        newsService.delete(newsId);
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * Создание новости
     * @param newsDto
     * @return NewsDto
     */
    @PostMapping
    public ResponseEntity<NewsDto> create(@RequestBody @Valid NewsNewRequestDto newsDto) {
        News news = newsService.create(mapperNews.toNewDomain(newsDto));
        return ResponseEntity.ok()
                .body(mapperNews.toDto(news));
    }

    /**
     * Обновление новости
     * @param newsId
     * @param newsDto
     * @return NewsDto
     */
    @PatchMapping("/{newsId}")
    public ResponseEntity<NewsDto> update(@PathVariable @NotNull Long newsId,
                                          @RequestBody @Valid NewsUpdateRequestDto newsDto) {
        News updatedNews = newsService.update(newsId,mapperNews.toUpdatedDomain(newsDto));
        return ResponseEntity.ok()
                .body(mapperNews.toDto(updatedNews));
    }

    /**
     * Поиск новости по содержанию или названию
     * @param query
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    @GetMapping("/search")
    public ResponseEntity<List<NewsDto>> searchByTitleAndText(@RequestParam @NotBlank String query,
                                                              @RequestParam(defaultValue = "0") int pageNumber,
                                                              @RequestParam(defaultValue = "10") int pageSize) {
        List<News> news = newsService.readByQuery(query, pageNumber, pageSize);
        return ResponseEntity.ok()
                .body(mapperNews.toDto(news));

    }

    /**
     * Получение новости с комментариями с пагинацией
     * @param newsId
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    @GetMapping("/{newsId}/comments")
    public ResponseEntity<NewsDtoWithComments> getNewsWithComments(@PathVariable @NotNull Long newsId,
                                                                   @RequestParam(required = false,defaultValue = "0") int pageNumber,
                                                                   @RequestParam(required = false,defaultValue = "10") int pageSize) {

        NewsDtoWithComments newsWithComments = mapperNews.toDtoWithComments(newsService.readByIdWithComments(newsId,pageNumber,pageSize));
        return ResponseEntity.ok()
                .body(newsWithComments);
    }

    /**
     * Получение комментария для новости
     * @param newsId
     * @param commentId
     * @return CommentResponse
     */
    @GetMapping("/{newsId}/comments/{commentId}")
    public ResponseEntity<CommentResponse> findNewsComment(@PathVariable("newsId") @NotNull Long newsId,
                                                           @PathVariable("commentId") @NotNull Long commentId) {
        Comment newsComment = newsService.getNewsComment(newsId, commentId);
        return ResponseEntity.ok()
                .body(commentMapper.toDto(newsComment));
    }


}
