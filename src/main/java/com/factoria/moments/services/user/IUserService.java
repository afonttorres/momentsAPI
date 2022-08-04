package com.factoria.moments.services.user;

import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;

import java.util.List;

public interface IUserService{

    List<UserNoPassResDto> findAll();
    UserNoPassResDto update(UserUpdateReqDto userUpdateReqDto, Long id);
    UserNoPassResDto getById(Long id);
}
