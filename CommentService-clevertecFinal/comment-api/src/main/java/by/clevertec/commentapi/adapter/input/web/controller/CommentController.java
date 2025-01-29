package by.clevertec.commentapi.adapter.input.web.controller;

import by.clevertec.commentapi.adapter.input.web.dto.request.CommentNewRequestDto;
import by.clevertec.commentapi.adapter.input.web.dto.request.CommentUpdateRequestDto;
import by.clevertec.commentapi.adapter.input.web.dto.response.CommentDto;
import by.clevertec.commentapi.mapper.CommentMapper;
import by.clevertec.commentcore.domain.Comment;
import by.clevertec.commentcore.port.input.CommentUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
 * Контроллер работы с комментариями
 */
@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentUseCase commentService;
    private final CommentMapper commentMapper;

    /**
     * Удаление комментария по id
     * @param commentId
     */
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteCommentById(@PathVariable @NotNull Long commentId) {
        commentService.delete(commentId);
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * Удаление комментария по id новости
     * @param newsId
     */
    @DeleteMapping("/news/{newsId}")
    public ResponseEntity<Void> deleteCommentByNewsId(@PathVariable @NotNull Long newsId) {
        commentService.deleteAllByNews(newsId);
        return ResponseEntity.noContent()
                .build();
    }

    /**
     * Создание комментария
     * @param commentDto
     * @return CommentDto
     */
    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody @Valid CommentNewRequestDto commentDto) {
        Comment comment = commentService.create(commentMapper.toNewDomain(commentDto));
        return ResponseEntity.ok()
                .body(commentMapper.toDto(comment));
    }

    /**
     * Обновление комментария
     * @param commentId
     * @param commentDto
     * @return CommentDto
     */
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable @NotNull Long commentId,
                                                    @RequestBody @Valid CommentUpdateRequestDto commentDto) {
        Comment updatedComment = commentService.update(commentId,commentMapper.toUpdateDomain(commentDto));
        return ResponseEntity.ok()
                .body(commentMapper.toDto(updatedComment));
    }

    /**
     * Получение комментария по id
     * @param commentId
     * @return CommentDto
     */
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable @NotNull Long commentId) {
        Comment comment = commentService.readById(commentId);
        return ResponseEntity.ok()
                .body(commentMapper.toDto(comment));
    }

    /**
     * Получение комментариев с пагинацией
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    @GetMapping
    public ResponseEntity<List<CommentDto>> getAll(@RequestParam(defaultValue = "0") int pageNumber,
                                                   @RequestParam(defaultValue = "10") int pageSize) {
        List<Comment> comments = commentService.readAllByPage(pageNumber, pageSize);

        return ResponseEntity.ok()
                .body(commentMapper.toDto(comments));
    }

    /**
     * Получение комментариев по id новости с пагинацией
     * @param newsId
     * @param pageNumber
     * @param pageSize
     * @return List
     */
    @GetMapping("/news/{newsId}")
    public ResponseEntity<List<CommentDto>> getAllByNewsId(@PathVariable @NotNull Long newsId,
                                                           @RequestParam(defaultValue = "0") int pageNumber,
                                                           @RequestParam(defaultValue = "10") int pageSize) {
        List<Comment> comments = commentService.readCommentsByNewsId(newsId,pageNumber, pageSize);

        return ResponseEntity.ok()
                .body(commentMapper.toDto(comments));
    }

}
