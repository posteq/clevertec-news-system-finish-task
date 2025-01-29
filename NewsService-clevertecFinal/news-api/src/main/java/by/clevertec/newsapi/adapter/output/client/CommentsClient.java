package by.clevertec.newsapi.adapter.output.client;

import by.clevertec.newsapi.adapter.input.web.dto.response.comment.CommentResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
/**
 * Клиент общения с микросервисом Comment
 */
@FeignClient(value = "comments-service" , url = "${application.service.url}")
public interface CommentsClient {

    /**
     * Получение списка комментариев с пагинацией по id новости
     * @param newsId
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    @GetMapping("/comment/news/{newsId}")
    List<CommentResponse> getCommentsByNews(@PathVariable @NotNull Long newsId,
                                            @RequestParam(required = false) Integer pageNumber,
                                            @RequestParam(required = false) Integer pageSize);

    /**
     * Получение комментария по id
     * @param commentId
     * @return CommentResponse
     */
    @GetMapping("/comment/{commentId}")
    CommentResponse getComment(@PathVariable @NotNull Long commentId);

    /**
     * Удаление комментария по id новости
     * @param newsId
     */
    @DeleteMapping("/comment/news/{newsId}")
    void deleteCommentsByNewsId(@PathVariable @NotNull Long newsId);
}
