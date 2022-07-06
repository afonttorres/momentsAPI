package com.factoria.moments.services.user;

import com.factoria.moments.dtos.user.request.UserLogReqDto;
import com.factoria.moments.dtos.user.request.UserPostReqDto;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.models.User;

import java.util.List;

public interface IUserService{
    User findById(Long id);

    List<UserNoPassResDto> findAll();

    UserNoPassResDto create(UserPostReqDto userPostReqDto);

    UserNoPassResDto update(UserUpdateReqDto userUpdateReqDto, Long id);

    UserNoPassResDto log(UserLogReqDto userLogReqDto);

    UserNoPassResDto getById(Long id);
}
