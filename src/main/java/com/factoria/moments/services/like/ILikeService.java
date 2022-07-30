package com.factoria.moments.services.like;

import com.factoria.moments.dtos.likes.LikeReqDto;
import com.factoria.moments.dtos.likes.LikeResDto;
import com.factoria.moments.models.User;

import java.util.List;

public interface ILikeService {
    List<LikeResDto> getAll();
    List<LikeResDto> getMomentLikes(Long id);

    boolean toggleLike(LikeReqDto like, User auth);


}
