package com.factoria.moments.services.comment;

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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentServiceTest {

    @Mock
    ICommentRepository commentRepository;
    IUserRepository userRepository;
    IMomentsRepository momentsRepository;

    @Test
    void findAllShouldReturnAllComents() {
        var commentService = new CommentService(commentRepository, momentsRepository, userRepository);
        Moment moment = this.createMoment();
        User creator = new User();
        List<Comment> comments = List.of(new Comment(), new Comment(), new Comment());
        comments.forEach(Comment -> {
            Comment.setMoment(moment);
            Comment.setCreator(creator);
        });
        Mockito.when(commentRepository.findAll()).thenReturn(comments);
        var sut = commentRepository.findAll();
        assertThat(sut.size(), equalTo(3));
    }

    @Test
    void createShoudCreateNewCommentFromReq() {
    }

    @Test
    void getByMomentShouldReturnCommentWithPamMomentId() {
    }

    private Moment createMoment(){
        var creator = new User();
        creator.setId(1L);
        var moment =  new Moment();
        moment.setCreator(creator);
        return moment;
    }
}

