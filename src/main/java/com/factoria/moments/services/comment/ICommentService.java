package com.factoria.moments.services.comment;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.models.User;

import java.util.List;

public interface ICommentService {
    List<CommentResDto> findAll();

    CommentResDto create(CommentRequestDto newComment, User auth);

    List<CommentResDto> getByMoment(Long id);
}
