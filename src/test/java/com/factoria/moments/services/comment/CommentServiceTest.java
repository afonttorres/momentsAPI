package com.factoria.moments.services.comment;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.CommentMapper;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CommentServiceTest {

    @Mock
    ICommentRepository commentRepository;
    @Mock
    IUserRepository userRepository;
    @Mock
    IMomentsRepository momentsRepository;

    @Test
    void findAllShouldReturnAllComents() {
        var commentService = new CommentService(commentRepository, momentsRepository, userRepository);
        Moment moment = this.createMoment();
        Comment comment = this.createComment(moment);
        List<Comment> comments = List.of(comment, comment, comment);
        Mockito.when(commentRepository.findAll()).thenReturn(comments);
        var sut = commentService.findAll();
        assertThat(sut.size(), equalTo(3));
    }

    @Test
    void getByMomentShouldReturnCommentWithPamMomentId() {
        var commentService = new CommentService(commentRepository, momentsRepository, userRepository);
        Moment moment = this.createMoment();
        Comment comment = this.createComment(moment);
        List<Comment> comments = List.of(comment, comment, comment);
        Mockito.when(commentRepository.findByMomentId(any(Long.class))).thenReturn(comments);
        var sut = commentService.getByMoment(1L);
        assertThat(sut.get(0).getMomentId(), equalTo(1L));
    }

    @Test
    void createShoudCreateNewCommentFromReq() {
        var commentService = new CommentService(commentRepository, momentsRepository, userRepository);
        CommentRequestDto req = new CommentRequestDto("comment", 1L, 2L);
        Moment moment = this.createMoment();
        User creator = this.createUser();
        Comment comment = new CommentMapper().mapReqToComment(req, moment, creator);
        CommentResDto res = new CommentMapper().mapCommentToRes(comment);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(creator));
        Mockito.when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        var sut = commentService.create(req, creator);
        assertThat(sut.getMomentId(), equalTo(res.getMomentId()));
        assertThat(sut.getCreator().getId(), equalTo(res.getCreator().getId()));
        assertThat(sut, equalTo(res));
    }

    @Test
    void createThrowsNotFoundException(){
        var commentService = new CommentService(commentRepository, momentsRepository, userRepository);
        CommentRequestDto req = new CommentRequestDto("comment", 1L, 2L);
        Moment moment = this.createMoment();
        User creator = this.createUser();
        Exception ex = assertThrows(NotFoundException.class, ()->{
            commentService.create(req, creator);
        });
        var res = "Moment Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }


    private Moment createMoment(){
        var creator = new User();
        creator.setId(1L);
        var moment =  new Moment();
        moment.setId(1L);
        moment.setCreator(creator);
        return moment;
    }

    private User createUser(){
        User user = new User();
        user.setId(2L);
        return user;
    }

    private Comment createComment(Moment moment){
        User creator = new User();
        creator.setId(2L);
        Comment comment = new Comment();
        comment.setCreator(creator);
        comment.setMoment(moment);
        return comment;
    }
}

