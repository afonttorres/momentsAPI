package com.factoria.moments.controllers;

import com.factoria.moments.dtos.comment.CommentRequestDto;
import com.factoria.moments.dtos.comment.CommentResDto;
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

    public CommentController (ICommentService commentService){
        this.commentService = commentService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/comments")
   ResponseEntity< List<CommentResDto>> getAll(){
        return new ResponseEntity<>(commentService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/comments")
    ResponseEntity<CommentResDto> createComment(@RequestBody CommentRequestDto req){
        return new ResponseEntity<>(commentService.create(req), HttpStatus.OK);
    }

    @GetMapping("/moments/{id}/comments")
    ResponseEntity<List<CommentResDto>> getMomentComments(@PathVariable Long id){
        return new ResponseEntity<>(commentService.getByMoment(id), HttpStatus.OK);
    }
}
