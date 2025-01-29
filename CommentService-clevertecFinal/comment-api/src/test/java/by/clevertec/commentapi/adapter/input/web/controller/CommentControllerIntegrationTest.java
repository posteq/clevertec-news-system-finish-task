package by.clevertec.commentapi.adapter.input.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
class CommentControllerIntegrationTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldThrowErrorWhenGetCommentById() {
        assertThat(mockMvcTester.get()
                .uri("/comments/{id}", 5000L))
                .hasStatus(HttpStatus.BAD_REQUEST);
    }


    @Test
    void shouldThrowErrorWhenGetAllComment() {
        MvcTestResult result = mockMvcTester.get()
                .uri("/comments")
                .param("page", "0.8")
                .param("size", "5").exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }


    @Test
    void shouldThrowErrorWhenCreateComment() {
        MvcTestResult result = mockMvcTester.post()
                .uri("/comments").contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                      {
                                        "text": "Test comment"
                                      }
                                """)
                .exchange();
        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }

    @Test
    void shouldThrowErrorWhenUpdateComment() {
        MvcTestResult result = mockMvcTester.put()
                .uri("/comments/{id}",
                        700)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                      {
                                        "text": "Test comment"
                                      }
                                """)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
    }
}
