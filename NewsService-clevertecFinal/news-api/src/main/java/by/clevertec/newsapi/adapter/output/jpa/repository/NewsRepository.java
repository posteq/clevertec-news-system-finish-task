package by.clevertec.newsapi.adapter.output.jpa.repository;

import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Репозиторий новостей
 */

public interface NewsRepository extends JpaRepository<NewsEntity, Long> {

    /**
     * Поиск новостей по названию и тексту с пагинацией
     * @param query
     * @param pageable
     */
    @Query(value = "SELECT * FROM news n WHERE to_tsvector('russian', n.title || ' ' || n.text) " +
            "@@ to_tsquery(:query || ':*')",
            nativeQuery = true)
    Page<NewsEntity> findByQuery(@Param("query") String query, Pageable pageable);

}