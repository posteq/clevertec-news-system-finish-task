package by.clevertec.commentcore.service;

import by.clevertec.commentcore.domain.Comment;
import by.clevertec.commentcore.domain.CreatedComment;
import by.clevertec.commentcore.port.input.CommentUseCase;
import by.clevertec.commentcore.port.output.CommentPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис работы с новостями
 * @see CommentUseCase
 */
@Service
@RequiredArgsConstructor
public class CommentService implements CommentUseCase {

    private final CommentPersistencePort commentPersistencePort;

    @Override
    public Comment readById(Long id) {
        return commentPersistencePort.readById(id);
    }

    @Override
    public void delete(Long id) {
        commentPersistencePort.delete(id);
    }

    @Override
    public Comment update(Long id, CreatedComment commentDomain) {
        Comment comment = commentPersistencePort.readById(id);
        comment.setText(commentDomain.getText());
        return commentPersistencePort.create(comment);
    }

    @Override
    public List<Comment> readCommentsByNewsId(Long newsId , int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return commentPersistencePort.readCommentByNewsId(newsId, pageRequest);
    }

    @Override
    public void deleteAllByNews(Long newsId) {
        commentPersistencePort.deleteAllByNews(newsId);
    }

    @Override
    public Comment create(CreatedComment newDomain) {
        Comment comment = new Comment();
        comment.setText(newDomain.getText());
        comment.setUsername(newDomain.getUsername());
        comment.setNewsId(newDomain.getNewsId());
        return commentPersistencePort.create(comment);
    }

    @Override
    public List<Comment> readAllByPage(int pageNumber, int pageSize) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return commentPersistencePort.readAllByPage(pageRequest);
    }
}
