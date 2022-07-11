package com.factoria.moments.mappers;

import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.models.User;

public class UserMapper {
    public UserResDtoMoment mapUserToResDtoMoment(User user){
        UserResDtoMoment resDto = new UserResDtoMoment();
        resDto.setName(user.getName());
        resDto.setUsername(user.getUsername());
        resDto.setAvatarUrl(user.getAvatarUrl());
        resDto.setId(user.getId());
        return resDto;
    }
}
