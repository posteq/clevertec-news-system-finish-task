package by.clevertec.commentcore.service;

import by.clevertec.commentcore.domain.Comment;
import by.clevertec.commentcore.domain.CreatedComment;
import by.clevertec.commentcore.port.output.CommentPersistencePort;
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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    CommentPersistencePort adapter;

    @InjectMocks
    CommentService service;

    @Test
    void shouldReadById() {
        //given
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);

        //when
        when(adapter.readById(id)).thenReturn(comment);

        //then
        Comment result = service.readById(id);
        assertEquals(result,comment);
    }

    @Test
    void shouldUpdate() {
        //given
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);
        comment.setText("test");

        Comment expected = new Comment();
        expected.setId(id);
        expected.setText("updated Text");

        CreatedComment updatedComment = new CreatedComment();
        updatedComment.setText("updated Text");

        //when
        when(adapter.readById(id)).thenReturn(comment);
        when(service.update(id,updatedComment)).thenReturn(expected);

        //then

        Comment actual = service.update(id, updatedComment);
        assertEquals(expected,actual);
    }

    @Test
    void shouldReadCommentsByNewsId() {
        //given
        Long id = 1L;
        Comment comment = new Comment();
        comment.setId(id);
        comment.setNewsId(id);

        Pageable pageRequest = PageRequest.of(0, 10);

        //when
        when(adapter.readCommentByNewsId(id,pageRequest)).thenReturn(List.of(comment));

        //then
        List<Comment> comments = service.readCommentsByNewsId(id, 0,10);
        assertTrue(comments.stream()
                .allMatch(c -> c.getId().equals(id)));
    }

    @Test
    void shouldDeleteAllByNews() {
        //given
        Long id = 1L;

        //when

        //then
        service.deleteAllByNews(id);
        verify(adapter).deleteAllByNews(id);
    }

    @Test
    void shouldCreate() {
        //given
        Long id = 1L;
        String text = "created Text";
        String username = "username";

        CreatedComment createdComment = new CreatedComment();
        createdComment.setText(text);
        createdComment.setUsername(username);
        createdComment.setNewsId(id);

        Comment expected = new Comment();
        expected.setText(text);
        expected.setUsername(username);
        expected.setNewsId(id);

        //when
        when(adapter.create(any(Comment.class))).thenReturn(expected);
        //then
        Comment actualComment = service.create(createdComment);
        assertNotNull(actualComment);
        assertEquals(expected.getText(), actualComment.getText());
        assertEquals(expected.getUsername(), actualComment.getUsername());
        assertEquals(expected.getNewsId(), actualComment.getNewsId());

        verify(adapter, times(1)).create(any(Comment.class));
    }

    @Test
    void ShouldReadAllByPage() {
        //given
        Comment comment1 = new Comment();
        comment1.setText("First comment");
        comment1.setUsername("user1");
        comment1.setNewsId(1L);

        Comment comment2 = new Comment();
        comment2.setText("Second comment");
        comment2.setUsername("user2");
        comment2.setNewsId(1L);

        List<Comment> expectedComments = Arrays.asList(comment1, comment2);
        Pageable pageRequest = PageRequest.of(0, 10);
        //when
        when(adapter.readAllByPage(pageRequest)).thenReturn(expectedComments);

        //then
        List<Comment> actualComments = service.readAllByPage(0, 10);

        assertNotNull(actualComments);
        assertEquals(2, actualComments.size());
        assertEquals(expectedComments.get(0).getText(), actualComments.get(0).getText());
        assertEquals(expectedComments.get(1).getText(), actualComments.get(1).getText());

        verify(adapter, times(1)).readAllByPage(pageRequest);
    }
}