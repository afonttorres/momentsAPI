package com.factoria.moments.services.comment;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.CommentMapper;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.services.moment.IMomentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService{

    private ICommentRepository commentRepository;
    private IMomentService momentService;
    private IAuthenticationFacade authenticationFacade;

    public CommentService(ICommentRepository commentRepository, IMomentService momentService, IAuthenticationFacade authenticationFacade) {
        this.commentRepository = commentRepository;
        this.momentService = momentService;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public List<CommentResDto> findAll() {
        return new CommentMapper().mapMultipleCommentsToRes( commentRepository.findAll());
    }

    @Override
    public List<CommentResDto> getByMoment(Long id) {
        return new CommentMapper().mapMultipleCommentsToRes(commentRepository.findByMomentId(id));
    }

    @Override
    public CommentResDto create(CommentRequestDto newComment) {
        var moment = momentService.momentValidation(newComment.getMomentId());
        User creator = authenticationFacade.getAuthUser();
        if(creator == null) throw new NotFoundException("User Not Found", "U-404");
        Comment comment = new CommentMapper().mapReqToComment(newComment, moment, creator);
        this.commentRepository.save(comment);
        return new CommentMapper().mapCommentToRes(comment);
    }
}
