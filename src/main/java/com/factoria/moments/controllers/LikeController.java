package com.factoria.moments.controllers;

import com.factoria.moments.dtos.likes.LikeReqDto;
import com.factoria.moments.models.Like;
import com.factoria.moments.services.like.ILikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LikeController {

    ILikeService likeService;

    public LikeController(ILikeService likeService){
        this.likeService = likeService;
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
    String like(@RequestBody LikeReqDto like){
        return likeService.toggleLike(like);
    }
}
