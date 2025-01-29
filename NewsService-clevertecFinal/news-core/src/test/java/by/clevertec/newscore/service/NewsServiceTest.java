package by.clevertec.newscore.service;

import by.clevertec.newscore.domain.news.CreatedNews;
import by.clevertec.newscore.domain.news.News;
import by.clevertec.newscore.port.output.NewsPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test class for the {@link NewsService}
 */
@ExtendWith(MockitoExtension.class)
class NewsServiceTest {

    @Mock
    private NewsPersistencePort adapter;

    @InjectMocks
    private NewsService service;

    @Test
    void shouldReadById() {
        //given
        Long id = 1L;
        News news = new News();
        news.setId(id);

        //when
        when(adapter.readNewsById(id)).thenReturn(news);

        //then
        News result = service.readById(id);
        assertEquals(result,news);
    }

    @Test
    void shouldDelete() {
        //given
        Long id = 1L;

        //when

        //then
        service.delete(id);
        verify(adapter).delete(id);

    }

    @Test
    void shouldUpdate() {
        //given
        Long id = 1L;
        News news = new News();
        news.setId(id);
        news.setTitle("title");
        news.setText("text");

        News expected = new News();
        expected.setId(id);
        expected.setTitle("updated title");
        expected.setText("updated text");

        CreatedNews createdNews = new CreatedNews();
        createdNews.setTitle("updated title");
        createdNews.setText("updated text");
        //when
        when(adapter.readNewsById(id)).thenReturn(news);
        when(service.update(id,createdNews)).thenReturn(expected);

        //then

        News actual = service.update(id, createdNews);
        assertEquals(expected,actual);
    }

    @Test
    void shouldCreate() {
        //given
        Long id = 1L;
        String title = "title";
        String text = "text";

        CreatedNews createdNews = new CreatedNews();
        createdNews.setTitle(title);
        createdNews.setText(text);

        News expected = new News();
        expected.setId(id);
        expected.setTitle(title);
        expected.setText(text);

        //when
        when(adapter.create(any(News.class))).thenReturn(expected);
        //then
        News actualNews = service.create(createdNews);
        assertNotNull(actualNews);
        assertEquals(expected.getText(), actualNews.getText());
        assertEquals(expected.getTitle(), actualNews.getTitle());

        verify(adapter, times(1)).create(any(News.class));
    }

    @Test
    void shouldReadAll() {
        //given
        Long id = 1L;
        String title = "title";
        String text = "text";

        News news1 = new News();
        news1.setId(id);
        news1.setTitle(title);
        news1.setText(text);

        News news2 = new News();
        news2.setId(id);
        news2.setTitle(title);
        news2.setText(text);

        List<News> expectedNews = Arrays.asList(news1, news2);

        //when
        when(adapter.readAllNews()).thenReturn(expectedNews);

        //then
        List<News> actualNews = service.readAll();
        assertNotNull(actualNews);
        assertEquals(2, actualNews.size());
        assertEquals(expectedNews.get(0).getText(), actualNews.get(0).getText());
        assertEquals(expectedNews.get(1).getText(), actualNews.get(1).getText());

    }

    @Test
    void shouldReadAllByPage() {
        //given
        String title = "title";
        String text = "text";

        News news1 = new News();
        news1.setId(1L);
        news1.setTitle(title);
        news1.setText(text);

        News news2 = new News();
        news2.setId(2L);
        news2.setTitle(title);
        news2.setText(text);

        List<News> expectedNews = Arrays.asList(news1, news2);
        Pageable pageRequest = PageRequest.of(0, 10);

        //when
        when(adapter.readAllNews(pageRequest)).thenReturn(expectedNews);

        //then
        List<News> actualNews = service.readAllByPage(0, 10);
        assertNotNull(actualNews);
        assertEquals(2, actualNews.size());
        assertEquals(expectedNews.get(0).getText(), actualNews.get(0).getText());
        assertEquals(expectedNews.get(1).getText(), actualNews.get(1).getText());
    }

    @Test
    void shouldReadByQuery() {
        //given
        News news1 = new News();
        news1.setId(1L);
        news1.setTitle("Shinku La");
        news1.setText("Shinku (or Shingo[1]) La is a mountain pass in India, on the state boundary " +
                "between Ladakh and Himachal Pradesh.[2] It connects the Lahaul region of Himachal" +
                " Pradesh with the Zanskar region of Ladakh. The MYFTESTEDWORD Nimmu-Padum-Darcha road strategic" +
                " road runs over the pass. The Shingo La Tunnel is planned under the pass. It is " +
                "expected to be completed by 2028 and will reduce the Manali to Kargil distance by 522 km");

        News news2 = new News();
        news2.setId(2L);
        news2.setTitle("Telephone (song)");
        news2.setText("\"Telephone\" is a song by American singer Lady Gaga from her third extended play (EP)," +
                " The Fame Monster (2009)—the reissue of her debut studio MYTESTEDWORD album, The Fame (2008). Featuring " +
                "American singer Beyoncé, it was released as the EP's second single on January 26, 2010. Gaga" +
                " and Rodney Jerkins wrote and produced \"Telephone\", with additional songwriting by LaShawn " +
                "Daniels");

        List<News> expectedNews = Arrays.asList(news1, news2);
        Pageable pageRequest = PageRequest.of(0, 10);

        //when
        when(adapter.readByQuery("MYTESTEDWORD",pageRequest)).thenReturn(expectedNews);
        when(service.readByQuery("MYTESTEDWORD",0,10)).thenReturn(expectedNews);
        //then
        List<News> actualNews = service.readByQuery("MYTESTEDWORD", 0, 10);
        assertNotNull(actualNews);
        assertEquals(2, actualNews.size());
        assertEquals(expectedNews.get(0).getText(), actualNews.get(0).getText());
        assertEquals(expectedNews.get(1).getText(), actualNews.get(1).getText());
    }

}