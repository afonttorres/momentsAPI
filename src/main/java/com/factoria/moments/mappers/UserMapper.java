package com.factoria.moments.mappers;

import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    //RES
    public UserResDtoMoment mapUserToResDtoMoment(User user){
        UserResDtoMoment res = new UserResDtoMoment();
        res.setName(user.getName());
        res.setUsername(user.getUsername());
        res.setAvatarUrl(user.getAvatarUrl());
        res.setId(user.getId());
        return res;
    }

    public UserNoPassResDto mapUserToNoPassResDto(User user){
        UserNoPassResDto res = new UserNoPassResDto();
        res.setId(user.getId());
        res.setUsername(user.getUsername());
        res.setAvatarUrl(user.getAvatarUrl());
        res.setEmail(user.getEmail());
        res.setName(user.getName());
        res.setBannerUrl(user.getBannerUrl());
        res.setFollowers(user.getFollowers());
        res.setFollowing(user.getFollowing());
        res.setDescription(user.getDescription());
        return res;
    }

    //REQ
    public User mapPutReqToUser(UserUpdateReqDto req, User user){
        user.setUsername(req.getUsername());
        user.setAvatarUrl(req.getAvatarUrl());
        user.setEmail(req.getEmail());
        user.setName(req.getName());
        user.setBannerUrl(req.getBannerUrl());
        user.setFollowers(req.getFollowers());
        user.setFollowing(req.getFollowing());
        user.setDescription(req.getDescription());
        return user;
    }

    public List<UserNoPassResDto> mapMultipleUsersToNoPassResDto(List<User> users){
        List<UserNoPassResDto> res = new ArrayList<>();
        users.forEach(User -> res.add(this.mapUserToNoPassResDto(User)));
        return res;
    }
}
