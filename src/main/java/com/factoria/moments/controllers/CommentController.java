package com.factoria.moments.controllers;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.models.Comment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.services.comment.ICommentService;
import com.factoria.moments.services.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="http://localhost:3000/")
public class CommentController {

   ICommentService commentService;
   IUserService userService;

    public CommentController (ICommentService commentService , IUserService userService){
        this.commentService = commentService;
        this.userService = userService;
    }

    private User getAuth(Long id){
        return userService.findById(id);
    }


    @GetMapping("/comments")
    List<CommentResDto> getAll(){
        return commentService.findAll();
    }

    @PostMapping("/comments")
    ResponseEntity<CommentResDto> createComment(@RequestBody CommentRequestDto newComment){
        User auth = this.getAuth(1L);
        var comment = commentService.create(newComment, auth);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @GetMapping("/moments/{id}/comments")
    List<CommentResDto> getMomentComments(@PathVariable Long id){
        return commentService.getByMoment(id);
    }
}
