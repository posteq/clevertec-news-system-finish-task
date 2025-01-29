package by.clevertec.newsapi.adapter.output.jpa.repository;

import by.clevertec.newsapi.adapter.output.jpa.entity.NewsEntity;
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

/**
 * Test class for the {@link NewsRepository}
 */
@DataJpaTest
@Testcontainers
@ActiveProfiles("test")
class NewsRepositoryTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private NewsRepository newsRepository;

    @Test
    void whenGet_shouldFindByQuery_Successful() {
        String query = "seaman";
        Pageable pageRequest = PageRequest.of(0, 10);
        List<NewsEntity> list = newsRepository.findByQuery(query, pageRequest).stream().toList();
        assertEquals(1, list.size());
    }

    @Test
    void whenGet_shouldNotFindByQuery_NotFound() {
        String query = "sdfasvwvwv";
        Pageable pageRequest = PageRequest.of(0, 10);
        List<NewsEntity> list = newsRepository.findByQuery(query, pageRequest).stream().toList();
        assertEquals(0, list.size());
    }
}