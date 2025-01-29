package by.clevertec.newsapi.adapter.input.web.controller;

import by.clevertec.newsapi.adapter.util.DocGenerated;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link NewsController}
 */
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ActiveProfiles("test")
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
@ExtendWith(RestDocumentationExtension.class)
class NewsControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void shouldGetById() throws Exception {
        mockMvc.perform(get("/news/{id}", "1"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(1),
                        jsonPath("$.title").value("Тестовый заголовок"),
                        jsonPath("$.text").value("Тест составленный для проверки работоспособности поиска в микросервисе news")
                )
                .andDo(DocGenerated.documentGetById());
    }

    @Test
    void shouldThrowWhenGetById_IdNotFound() throws Exception {
        mockMvc.perform(get("/news/{id}", "100000"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetAllByPage() throws Exception {
        mockMvc.perform(get("/news")
                        .param("pageNumber", "0")
                        .param("pageSize", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andDo(DocGenerated.documentGetAllByPage());
    }

    @Test
    void shouldCreate() throws Exception {
        String newsDto = """
                {
                    "title": "Проверочная новая новость",
                    "text": "Проверочная новая запись"
                }""";

        mockMvc.perform(post("/news")
                        .content(newsDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.title").value("Проверочная новая новость"),
                        jsonPath("$.text").value("Проверочная новая запись")
                        )
                .andDo(DocGenerated.documentCreate());
    }

    @Test
    void shouldThrowWhenCreate_NullTitle() throws Exception {
        String newsDto = """
                {
                    "title": null,
                    "text": "Проверочная новая запись"
                }""";

        mockMvc.perform(post("/news")
                        .content(newsDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        String newsDto = """
                {
                    "title": "Обновленный проверочный заголовок",
                    "text": "Обновленная проверочная запись"
                }""";

        mockMvc.perform(patch("/news/{id}", "2")
                        .content(newsDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                        jsonPath("$.id").value(2),
                        jsonPath("$.title").value("Обновленный проверочный заголовок"),
                        jsonPath("$.text").value("Обновленная проверочная запись")
                )
                .andDo(DocGenerated.documentUpdate());
    }
    @Test
    void shouldThrowWhenUpdate_InvalidId() throws Exception {
        String newsDto = """
                {
                    "title": "Обновленный проверочный заголовок",
                    "text": "Обновленная проверочная запись"
                }""";

        mockMvc.perform(patch("/news/{id}", "200")
                        .content(newsDto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    @Test
     void shouldSearchByTitleAndText() throws Exception {
        mockMvc.perform(get("/news/search")
                        .param("query", "путешеств")
                        .param("pageNumber", "0")
                        .param("pageSize", "10"))
                .andExpect(status().isOk())
                .andDo(DocGenerated.documentSearchByTitleAndText());
    }
}
