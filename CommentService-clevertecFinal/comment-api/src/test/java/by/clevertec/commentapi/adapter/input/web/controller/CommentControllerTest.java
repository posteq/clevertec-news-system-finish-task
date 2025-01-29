package by.clevertec.commentapi.adapter.input.web.controller;

import by.clevertec.commentapi.adapter.input.web.dto.request.CommentNewRequestDto;
import by.clevertec.commentapi.adapter.input.web.dto.request.CommentUpdateRequestDto;
import by.clevertec.commentapi.adapter.input.web.dto.response.CommentDto;
import by.clevertec.commentapi.mapper.CommentMapper;
import by.clevertec.commentcore.domain.Comment;
import by.clevertec.commentcore.domain.CreatedComment;
import by.clevertec.commentcore.port.input.CommentUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CommentUseCase service;

    @Mock
    private CommentMapper mapper;

    @InjectMocks
    private CommentController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldDeleteCommentById_thenReturnHttpOk() throws Exception {
        //given
        Long commentId = 1L;

        //when


        //then
        mockMvc.perform(delete("/comment/{commentId}", commentId))
                .andExpect(status().isNoContent());

        verify(service, times(1)).delete(commentId);
    }

    @Test
    void whenDeleteCommentByNewsId_thenReturnHttpOk() throws Exception {
        //given
        Long newsId = 1L;

        //when

        //then
        mockMvc.perform(delete("/comment/news/{newsId}", newsId))
                .andExpect(status().isNoContent());

        verify(service, times(1)).deleteAllByNews(newsId);
    }

    @Test
    void shouldCreateComment_thenReturnHttpOk() throws Exception {
        //given
        String text = "New Comment";
        String username = "username";
        Long id = 1L;

        CommentNewRequestDto commentDto = new CommentNewRequestDto(text, username, id);

        Comment comment = new Comment();
        comment.setText(text);
        comment.setUsername(username);
        comment.setNewsId(id);

        CreatedComment createdComment = new CreatedComment();
        createdComment.setNewsId(id);
        createdComment.setText(text);
        createdComment.setUsername(username);

        CommentDto commentResponseDto = new CommentDto(id,null,text,username);

        //when
        when(mapper.toNewDomain(commentDto)).thenReturn(createdComment);
        when(service.create(createdComment)).thenReturn(comment);
        when(mapper.toDto(comment)).thenReturn(commentResponseDto);

        //then
        mockMvc.perform(post("/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"New Comment\"," +
                                " \"username\":\"username\", " +
                                "\"newsId\":1}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service, times(1)).create(createdComment);
    }

    @Test
    void shouldUpdateComment_thenReturnHttpOk() throws Exception {
        //given
        Long id = 1L;
        String text = "Updated Comment";
        String username = "username";

        CommentUpdateRequestDto commentDto = new CommentUpdateRequestDto(text);
        CommentDto commentResponseDto = new CommentDto(id,null,text,username);

        CreatedComment createdComment = new CreatedComment();
        createdComment.setNewsId(id);
        createdComment.setText(text);
        createdComment.setUsername(username);

        Comment comment = new Comment();
        comment.setId(id);
        comment.setText(text);
        comment.setUsername(username);
        comment.setNewsId(id);

        //when
        when(mapper.toUpdateDomain(commentDto)).thenReturn(createdComment);
        when(service.update(id, createdComment)).thenReturn(comment);
        when(mapper.toDto(comment)).thenReturn(commentResponseDto);

        //then
        mockMvc.perform(patch("/comment/{commentId}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"text\":\"Updated Comment\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service, times(1)).update(id, createdComment);
    }

    @Test
    void testGetCommentById_thenReturnHttpOk() throws Exception {
        //given
        Long commentId = 1L;
        Comment comment = new Comment();
        CommentDto commentResponseDto = new CommentDto(commentId,null,null,null);

        //when
        when(service.readById(commentId)).thenReturn(comment);

        when(mapper.toDto(comment)).thenReturn(commentResponseDto);

        //then
        mockMvc.perform(get("/comment/{commentId}", commentId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verify(service, times(1)).readById(commentId);
    }
}