package com.factoria.moments.mappers;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;

public class CommentMapper {
    public CommentResDto mapCommentToRes(Comment comment){
        CommentResDto resDto = new CommentResDto();
        resDto.setId(comment.getId());
        resDto.setMomentId(comment.getMoment().getId());
        resDto.setComment(comment.getComment());
        resDto.setLiked(comment.isLiked());
        resDto.setCreator( new UserMapper().mapUserToResDtoMoment(comment.getCreator()));
        return resDto;
    }

    public Comment mapReqToComment(CommentRequestDto req, Moment moment, User creator){
        Comment comment = new Comment();
        comment.setComment(req.getComment());
        comment.setMoment(moment);
        comment.setCreator(creator);
        return comment;
    }
}
