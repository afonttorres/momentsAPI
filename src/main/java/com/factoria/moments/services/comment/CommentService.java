package com.factoria.moments.services.comment;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.CommentMapper;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService{

    private ICommentRepository commentRepository;
    private IMomentsRepository momentsRepository;
    private IUserRepository userRepository;

    public CommentService(ICommentRepository commentRepository, IMomentsRepository momentsRepository, IUserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.momentsRepository = momentsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentResDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        List <CommentResDto>castedComments = new ArrayList<>();
        comments.forEach(Comment -> {
            castedComments.add(new CommentMapper().mapCommentToRes(Comment));
        });
        return castedComments;
    }

    @Override
    public List<CommentResDto> getByMoment(Long id) {
        List<CommentResDto> momentComments = new ArrayList<>();
        commentRepository.findByMomentId(id).forEach(Comment ->{
           momentComments.add( new CommentMapper().mapCommentToRes(Comment));
        });
        return momentComments;
    }

    @Override
    public CommentResDto create(CommentRequestDto newComment, User auth) {
        var moment = this.momentsRepository.findById(newComment.getMomentId());
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        User creator = auth;
        Comment comment = new CommentMapper().mapReqToComment(newComment, moment.get(), creator);
        this.commentRepository.save(comment);
        return new CommentMapper().mapCommentToRes(comment);
    }
}
