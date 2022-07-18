package com.factoria.moments.services.like;

import com.factoria.moments.dtos.likes.LikeReqDto;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.User;

import java.util.List;

public interface ILikeService {
    List<Like> getAll();
    List<Like> getMomentLikes(Long id);

    String toggleLike(LikeReqDto like, User auth);


}
