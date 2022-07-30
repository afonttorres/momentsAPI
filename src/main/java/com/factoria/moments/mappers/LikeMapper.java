package com.factoria.moments.mappers;

import com.factoria.moments.dtos.likes.LikeDto;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;

import java.util.ArrayList;
import java.util.List;

public class LikeMapper {
    public Like mapReqToLike(User liker, Moment moment){
        Like like = new Like();
        like.setLiker(liker);
        like.setMoment(moment);
        return like;
    }

    public LikeDto mapLikeToLikeDto(Like like){
       return new LikeDto(like.getId(), like.getMoment().getId(), like.getLiker().getId());
    }

    public List<LikeDto> mapMultipleLikesToLikeDto(List<Like> likes){
        List<LikeDto> res = new ArrayList<>();
        likes.forEach(Like-> res.add(this.mapLikeToLikeDto(Like)));
        return res;
    }
}
