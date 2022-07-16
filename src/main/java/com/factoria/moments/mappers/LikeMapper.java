package com.factoria.moments.mappers;

import com.factoria.moments.models.Like;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;

public class LikeMapper {
    public Like mapReqToLike(User liker, Moment moment){
        Like like = new Like();
        like.setLiker(liker);
        like.setMoment(moment);
        return like;
    }
}
