package com.factoria.moments.controllers;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
import com.factoria.moments.models.User;
import com.factoria.moments.services.comment.ICommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class CommentController {

   ICommentService commentService;
   IAuthenticationFacade authenticationFacade;

    public CommentController (ICommentService commentService , IAuthenticationFacade authenticationFacade){
        this.commentService = commentService;
        this.authenticationFacade = authenticationFacade;
    }

    private User getAuth(){
        return authenticationFacade.getAuthUser();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/comments")
    List<CommentResDto> getAll(){
        return commentService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/comments")
    ResponseEntity<CommentResDto> createComment(@RequestBody CommentRequestDto newComment){
        User auth = this.getAuth();
        var comment = commentService.create(newComment, auth);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/moments/{id}/comments")
    List<CommentResDto> getMomentComments(@PathVariable Long id){
        return commentService.getByMoment(id);
    }
}
