package com.factoria.moments.services.comment;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.IUserRepository;
import com.factoria.moments.services.user.IUserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService implements ICommentService{

    private ICommentRepository commentRepository;
    private IMomentsRepository momentsRepository;
    private IUserRepository userRepository;

    public CommentService (ICommentRepository commentRepository, IMomentsRepository momentsRepository, IUserRepository userRepository){
        this.commentRepository = commentRepository;
        this.momentsRepository = momentsRepository;
        this.userRepository = userRepository;
    }


    @Override
    public List<CommentResDto> findAll() {
        List<Comment> comments = commentRepository.findAll();
        List <CommentResDto>castedComments = new ArrayList<>();
        comments.forEach(Comment -> {
            castedComments.add(castCommentToResComent(Comment));
        });
        return castedComments;
    }

    @Override
    public CommentResDto create(CommentRequestDto newComment) {
        Comment comment = new Comment();
        Moment moment = this.momentsRepository.findById(newComment.getMomentId()).get();
        User user = this.userRepository.findById(newComment.getUserId()).get();
        comment.setComment(newComment.getComment());
        comment.setMoment(moment);
        comment.setCreator(user);
        this.commentRepository.save(comment);
        return this.castCommentToResComent(comment);
    }

    @Override
    public List<CommentResDto> getByMoment(Long id) {
        /*Moment moment = momentsRepository.findById(id).get();
        List <CommentResDto>  momentComments = new ArrayList<>();
        if(moment.getCreator().getId() == null) return null;
        commentRepository.findAll().forEach(Comment ->{
          if(Comment.getMoment().getId() == id) {
              momentComments.add(this.castCommentToResComent(Comment));
          }
        });
        */
        List<CommentResDto> momentComments = new ArrayList<>();
        commentRepository.findByMomentId(id).forEach(Comment ->{
           momentComments.add( this.castCommentToResComent(Comment));
        });

        return momentComments;
    }

    private CommentResDto castCommentToResComent(Comment comment){
        CommentResDto resDto = new CommentResDto();
        resDto.setId(comment.getId());
        resDto.setMomentId(comment.getMoment().getId());
        resDto.setComment(comment.getComment());
        resDto.setLiked(comment.isLiked());
        resDto.setCreator(this.castUserToResUser(comment.getCreator()));
        return resDto;
    }

    private UserResDtoMoment castUserToResUser(User user){
        UserResDtoMoment resDto = new UserResDtoMoment();
        resDto.setName(user.getName());
        resDto.setUsername(user.getUsername());
        resDto.setAvatarUrl(user.getAvatarUrl());
        resDto.setId(user.getId());
        return resDto;
    }
}
