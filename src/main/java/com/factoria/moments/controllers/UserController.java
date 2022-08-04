package com.factoria.moments.controllers;

import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.services.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    ResponseEntity<List<UserNoPassResDto>> getAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    ResponseEntity<UserNoPassResDto> getById(@PathVariable Long id){
        return new ResponseEntity<>(userService.getById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/users/{id}")
    ResponseEntity<UserNoPassResDto> update(@RequestBody UserUpdateReqDto userUpdateReqDto, @PathVariable Long id){
        return new ResponseEntity<>(userService.update(userUpdateReqDto, id), HttpStatus.OK);
    }

}
