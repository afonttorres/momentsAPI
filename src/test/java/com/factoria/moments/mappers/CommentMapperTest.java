package com.factoria.moments.mappers;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentMapperTest {

    @Test
    void mapReqToComment() {
        var req = new CommentRequestDto("comment", 1L, 2L);
        var creator = this.createUser();
        var moment = this.createMoment();
        var sut = new CommentMapper().mapReqToComment(req, moment, creator);
        assertEquals(sut.getComment(), req.getComment());
        assertEquals(sut.getCreator().getId(), req.getUserId());
        assertEquals(sut.getMoment().getId(), req.getMomentId());
    }

    @Test
    void mapCommentToRes() {
        var comment = this.createComment();
        var sut = new CommentMapper().mapCommentToRes(comment);
        System.out.println(sut);
        assertEquals(sut.getComment(), comment.getComment());
        assertEquals(sut.getMomentId(), comment.getMoment().getId());
        assertEquals(sut.getCreator().getUsername(), comment.getCreator().getUsername());
    }

    public Moment createMoment(){
        var user = new User();
        user.setId(1L);
        var moment = new Moment();
        moment.setId(1L);
        moment.setDescription("desc");
        moment.setImgUrl("img");
        moment.setLocation("loc");
        moment.setCreator(user);
        return moment;
    }

    public User createUser(){
        User user = new User();
        user.setId(2L);
        return user;
    }

    public Comment createComment(){
        User creator = this.createUser();
        Moment moment = this.createMoment();
        Comment comment = new Comment();
        comment.setComment("comment");
        comment.setMoment(moment);
        comment.setCreator(creator);
        comment.setId(1L);
        return comment;
    }
}