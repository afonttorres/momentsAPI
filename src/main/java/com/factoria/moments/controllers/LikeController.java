package com.factoria.moments.controllers;

import com.factoria.moments.dtos.likes.LikeReqDto;
import com.factoria.moments.models.Like;
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
    List<Like> getAll(){
        return likeService.getAll();
    }

    @GetMapping("/moments/{id}/likes")
    List<Like> getMomentLikes(@PathVariable Long id){
        return likeService.getMomentLikes(id);
    }

    @PostMapping("/likes")
    ResponseEntity<Boolean> like(@RequestBody LikeReqDto like){
        User auth = this.getAuth(1L);
        var isLiked = likeService.toggleLike(like, auth);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }
}
