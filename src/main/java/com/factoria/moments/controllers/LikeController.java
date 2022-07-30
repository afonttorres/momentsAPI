package com.factoria.moments.controllers;

import com.factoria.moments.dtos.likes.LikeReqDto;
import com.factoria.moments.dtos.likes.LikeResDto;
import com.factoria.moments.models.User;
import com.factoria.moments.services.like.ILikeService;
import com.factoria.moments.services.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LikeController {

    ILikeService likeService;
    IUserService userService;

    public LikeController(ILikeService likeService, IUserService userService){
        this.likeService = likeService;
        this.userService = userService;
    }

    private User getAuth(Long id){
        return userService.findById(id);
    }


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

    @PostMapping("/likes")
    ResponseEntity<Boolean> like(@RequestBody LikeReqDto like){
        User auth = this.getAuth(1L);
        var isLiked = likeService.toggleLike(like, auth);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }
}
