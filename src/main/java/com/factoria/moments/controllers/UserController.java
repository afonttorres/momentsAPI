package com.factoria.moments.controllers;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.models.User;
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
    IAuthenticationFacade authenticationFacade;
    public UserController(IUserService userService, IAuthenticationFacade authenticationFacade){
        this.userService = userService;
        this.authenticationFacade = authenticationFacade;
    }

    public User getAuth(){
        return this.authenticationFacade.getAuthUser();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/users")
    List<UserNoPassResDto> getAll(){
        return userService.findAll();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/users/{id}")
    ResponseEntity<UserNoPassResDto> getById(@PathVariable Long id){
        var user = userService.getById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/users/{id}")
    ResponseEntity<UserNoPassResDto> update(@RequestBody UserUpdateReqDto userUpdateReqDto, @PathVariable Long id){
        var auth = this.getAuth();
        var user = userService.update(userUpdateReqDto, id, auth);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
