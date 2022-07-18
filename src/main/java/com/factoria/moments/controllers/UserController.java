package com.factoria.moments.controllers;

import com.factoria.moments.dtos.user.request.UserLogReqDto;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.dtos.user.request.UserPostReqDto;
import com.factoria.moments.services.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<UserNoPassResDto> getById(@PathVariable Long id){
        var user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/users")
    ResponseEntity<UserNoPassResDto> create(@RequestBody UserPostReqDto userPostReqDto){
        var user =  userService.create(userPostReqDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    ResponseEntity<UserNoPassResDto> update(@RequestBody UserUpdateReqDto userUpdateReqDto, @PathVariable Long id){
        var user = userService.update(userUpdateReqDto, id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/users/log")
    ResponseEntity<UserNoPassResDto> log(@RequestBody UserLogReqDto userLogReqDto){
        var user = userService.log(userLogReqDto);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
