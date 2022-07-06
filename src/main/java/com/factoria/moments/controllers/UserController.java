package com.factoria.moments.controllers;

import com.factoria.moments.dtos.user.request.UserLogReqDto;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.dtos.user.request.UserPostReqDto;
import com.factoria.moments.services.user.IUserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    IUserService userService;
    public UserController(IUserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    List<UserNoPassResDto> getAll(){
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    UserNoPassResDto getById(@PathVariable Long id){
        return userService.getById(id);
    }

    @PostMapping("/users")
    UserNoPassResDto create(@RequestBody UserPostReqDto userPostReqDto){
        return userService.create(userPostReqDto);
    }

    @PutMapping("/users/{id}")
    UserNoPassResDto update(@RequestBody UserUpdateReqDto userUpdateReqDto, @PathVariable Long id){
        return userService.update(userUpdateReqDto, id);
    }

    @PutMapping("/users/log")
    UserNoPassResDto update(@RequestBody UserLogReqDto userLogReqDto){
        return userService.log(userLogReqDto);
    }
}
