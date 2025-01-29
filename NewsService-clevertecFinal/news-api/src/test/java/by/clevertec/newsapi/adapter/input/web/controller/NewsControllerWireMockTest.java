package by.clevertec.newsapi.adapter.input.web.controller;


import by.clevertec.newsapi.adapter.input.web.dto.response.comment.CommentResponse;
import by.clevertec.newsapi.adapter.output.client.CommentsClient;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;

import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the {@link NewsController}
 */
@SpringBootTest
@EnableFeignClients
@AutoConfigureWireMock(port = 9999)
@ActiveProfiles("test")
class NewsControllerWireMockTest {

    @Autowired
    CommentsClient client;

    @Test
    void whenGet_shouldReturnComment_Successful() {
        //given

        //when
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/comment/2"))
                .willReturn(okJson("""
                            {
                                "id": 2,
                                "dateOfCreation": "2025-01-22T19:19:31.422357",
                                "text": "Holyshit",
                                "username": "Petr"
                            }
                        """)));

        //then
        CommentResponse actualResponse = client.getComment(2L);

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse.id()).isEqualTo(2);
        assertThat(actualResponse.dateOfCreation()).isEqualTo("2025-01-22T19:19:31.422357");
        assertThat(actualResponse.text()).isEqualTo("Holyshit");
        assertThat(actualResponse.username()).isEqualTo("Petr");
    }
}
