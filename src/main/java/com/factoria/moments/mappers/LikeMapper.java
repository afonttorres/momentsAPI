package com.factoria.moments.mappers;

import com.factoria.moments.dtos.likes.LikeResDto;
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

    public LikeResDto mapLikeToLikeResDto(Like like){
       return new LikeResDto(like.getId(), like.getMoment().getId(), like.getLiker().getId());
    }

    public List<LikeResDto> mapMultipleLikesToLikeDto(List<Like> likes){
        List<LikeResDto> res = new ArrayList<>();
        likes.forEach(Like-> res.add(this.mapLikeToLikeResDto(Like)));
        return res;
    }
}
