package com.factoria.moments.controllers;

import com.factoria.moments.dtos.likes.LikeResDto;
import com.factoria.moments.services.like.ILikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LikeController {

    ILikeService likeService;

    public LikeController(ILikeService likeService){
        this.likeService = likeService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/likes")
    ResponseEntity<List<LikeResDto>> getAll(){
        var likes = likeService.getAll();
        return new ResponseEntity<>(likes, HttpStatus.OK);
    }

    @GetMapping("/moments/{id}/likes")
    ResponseEntity<List<LikeResDto>> getMomentLikes(@PathVariable Long id){
        var likes = likeService.getMomentLikes(id);
        return new ResponseEntity<>(likes, HttpStatus.OK) ;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/moments/{id}/likes")
    ResponseEntity<Boolean> like(@PathVariable Long id){
        System.out.println(id);
        var isLiked = likeService.toggleLike(id);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }
}
