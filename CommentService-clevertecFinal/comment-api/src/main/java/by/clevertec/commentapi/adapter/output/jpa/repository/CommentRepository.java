package by.clevertec.commentapi.adapter.output.jpa.repository;

import by.clevertec.commentapi.adapter.output.jpa.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Репозиторий комментария
 */
@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    /**
     * Поиск новостей по id с пагинацией
     * @param newsId
     * @param pageable
     * @return Page
     */
    Page<CommentEntity> findByNewsId(Long newsId, Pageable pageable);

    /**
     * Удаление комментариев по id новостей
     * @param newsId
     */
    void deleteByNewsId(Long newsId);
}