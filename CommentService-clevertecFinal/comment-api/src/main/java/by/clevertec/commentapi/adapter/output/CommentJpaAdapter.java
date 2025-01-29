package by.clevertec.commentapi.adapter.output;

import by.clevertec.commentapi.adapter.output.jpa.entity.CommentEntity;
import by.clevertec.commentapi.adapter.output.jpa.repository.CommentRepository;
import by.clevertec.commentapi.exception.EntityFindException;
import by.clevertec.commentapi.mapper.CommentMapper;
import by.clevertec.commentcore.domain.Comment;
import by.clevertec.commentcore.port.output.CommentPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Адаптер работы с репозиторием, реализует посредством JPA
 * @see CommentPersistencePort
 */
@Component
@RequiredArgsConstructor
public class CommentJpaAdapter implements CommentPersistencePort {

    private final CommentRepository repository;
    private final CommentMapper commentMapper;

    @Override
    public Comment readById(Long id) {
        return commentMapper.toDomain(repository.findById(id)
                .orElseThrow(() -> new EntityFindException(id)));
    }

    @Override
    public List<Comment> readAllByPage(Pageable pageable) {
        return commentMapper.toDomain(repository.findAll(pageable).getContent());
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Comment create(Comment comment) {
        CommentEntity entity = commentMapper.toEntity(comment);
        return commentMapper.toDomain(repository.save(entity));
    }

    @Override
    public List<Comment> readCommentByNewsId(Long newsId, Pageable pageable) {
        return commentMapper.toDomain(repository.findByNewsId(newsId, pageable)
                .getContent());
    }

    @Transactional
    @Override
    public void deleteAllByNews(Long newsId) {
        repository.deleteByNewsId(newsId);
    }

}
