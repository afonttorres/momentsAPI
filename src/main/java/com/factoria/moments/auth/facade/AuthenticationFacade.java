package com.factoria.moments.auth.facade;

import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IAuthRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{

    @Autowired
    IAuthRepository authRepository;

    @Override
    public User getAuthUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return authRepository.findByUsername(username).get();
    }
}
