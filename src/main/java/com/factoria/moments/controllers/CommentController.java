package com.factoria.moments.controllers;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.services.comment.ICommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000/")
public class CommentController {

   ICommentService commentService;

    public CommentController (ICommentService commentService){
        this.commentService = commentService;
    }

    @GetMapping("/comments")
    List<CommentResDto> getAll(){
        return commentService.findAll();
    }

    @PostMapping("/comments")
    CommentResDto createComment(@RequestBody CommentRequestDto newComment){
        return commentService.create(newComment);
    }

    @GetMapping("/moments/{id}/comments")
    List<CommentResDto> getMomentComments(@PathVariable Long id){
        return commentService.getByMoment(id);
    }
}
