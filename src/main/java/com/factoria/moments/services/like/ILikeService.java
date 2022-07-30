package com.factoria.moments.services.like;

import com.factoria.moments.dtos.likes.LikeDto;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.User;

import java.util.List;

public interface ILikeService {
    List<LikeDto> getAll();
    List<LikeDto> getMomentLikes(Long id);

    boolean toggleLike(LikeDto like, User auth);


}
