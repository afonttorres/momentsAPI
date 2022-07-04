package com.factoria.moments.controllers;

import com.factoria.moments.dtos.CommentRequestDto;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000/")
public class CommentController {

    private ICommentRepository commentRepository;
    private IMomentsRepository momentsRepository;

    public CommentController (ICommentRepository commentRepository, IMomentsRepository momentsRepository){
        this.commentRepository = commentRepository;
        this.momentsRepository = momentsRepository;
    }

    @GetMapping("/comments")
    List<Comment> getAll(){
        return this.commentRepository.findAll();
    }

    @PostMapping("/comments")
    Comment createComment(@RequestBody CommentRequestDto newComment){
        Comment comment = new Comment();
        Moment moment = this.momentsRepository.findById(newComment.getMomentId()).get();
        comment.setMoment(moment);
        comment.setComment(newComment.getComment());
        comment.setUserId(newComment.getUserId());
        this.commentRepository.save(comment);
        return comment;
    }
}
