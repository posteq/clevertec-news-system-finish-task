package by.clevertec.newsapi.adapter.util;

import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

/**
 * Утилитный класс создания снипетов
 */

public class DocGenerated {
    public static RestDocumentationResultHandler documentGetById(){
        return document("get-news-by-id",
                pathParameters(
                        parameterWithName("id").description("Id новости")
                ),
                responseFields(
                        fieldWithPath("id").type("Long").description("Id новости"),
                        fieldWithPath("title").type("String").description("Название новости"),
                        fieldWithPath("text").type("String").description("Содержание новости"),
                        fieldWithPath("dateOfCreation").type("LocalDateTime").description("Содержание новости")
                ));
    }

    public static RestDocumentationResultHandler documentGetAllByPage() {
        return document("get-news-by-page",
                queryParameters(
                        parameterWithName("pageNumber").description("Номер страницы"),
                        parameterWithName("pageSize").description("Размер страницы")
                ),
                responseFields(
                        fieldWithPath("[].id").type("Long").description("Id новости"),
                        fieldWithPath("[].title").type("String").description("Название новости"),
                        fieldWithPath("[].text").type("String").description("Содержание новости"),
                        fieldWithPath("[].dateOfCreation").type("LocalDateTime").description("Дата создания новости")
                ));
    }

    public static RestDocumentationResultHandler documentCreate() {
        return document("create-news",
                requestFields(
                        fieldWithPath("title").type("String").description("Название новости"),
                        fieldWithPath("text").type("String").description("Содержание новости")
                ),
                responseFields(
                        fieldWithPath("id").type("Long").description("Id новой новости"),
                        fieldWithPath("title").type("String").description("Название новости"),
                        fieldWithPath("text").type("String").description("Содержание новости"),
                        fieldWithPath("dateOfCreation").type("LocalDateTime").description("Дата создания новости")
                ));
    }

    public static RestDocumentationResultHandler documentUpdate() {
        return document("update-news",
                pathParameters(
                        parameterWithName("id").description("Id новости для обновления")
                ),
                requestFields(
                        fieldWithPath("title").type("String").description("Обновленное название новости"),
                        fieldWithPath("text").type("String").description("Обновленное содержание новости")
                ),
                responseFields(
                        fieldWithPath("id").type("Long").description("Id обновленной новости"),
                        fieldWithPath("title").type("String").description("Обновленное название новости"),
                        fieldWithPath("text").type("String").description("Обновленное содержание новости"),
                        fieldWithPath("dateOfCreation").type("LocalDateTime").description("Дата создания новости")
                ));
    }

    public static RestDocumentationResultHandler documentSearchByTitleAndText() {
        return document("search-news",
                queryParameters(
                        parameterWithName("query").description("Поисковый запрос"),
                        parameterWithName("pageNumber").description("Номер страницы"),
                        parameterWithName("pageSize").description("Размер страницы")
                ),
                responseFields(
                        fieldWithPath("[]").description("Список новостей"),
                        fieldWithPath("[].id").type("Long").description("Id новости"),
                        fieldWithPath("[].title").type("String").description("Название новости"),
                        fieldWithPath("[].text").type("String").description("Содержание новости"),
                        fieldWithPath("[].dateOfCreation").type("LocalDateTime").description("Дата создания новости"))
        );
    }


    public static RestDocumentationResultHandler documentSearchCommentBYId() {
        return document("comments/get-comment",
             responseFields(
                        fieldWithPath("id").description("Id комментария"),
                        fieldWithPath("dateOfCreation").description("Дата создания комментария"),
                        fieldWithPath("text").description("Текст комментария"),
                        fieldWithPath("username").description("Имя пользователя, оставившего комментарий")
                        ));
    }
}
