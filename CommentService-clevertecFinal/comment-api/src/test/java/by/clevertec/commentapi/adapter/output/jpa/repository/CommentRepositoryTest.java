package by.clevertec.commentapi.adapter.output.jpa.repository;

import by.clevertec.commentapi.adapter.output.jpa.entity.CommentEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
class CommentRepositoryTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CommentRepository commentRepository;

    @Test
    void shouldFindByNewsId() {
        Long newsId = 1L;
        Pageable pageRequest = PageRequest.of(0, 10);

        List<CommentEntity> entities = commentRepository.findByNewsId(newsId, pageRequest).stream().toList();
        entities.forEach(System.out::println);
        assertEquals(2, entities.size());

    }

    @Test
    void shouldDeleteByNewsId() {
        Long newsId = 1L;
        commentRepository.deleteByNewsId(newsId);
        List<CommentEntity> entities = commentRepository.findAll();

        assertEquals(6,entities.size());
    }
}