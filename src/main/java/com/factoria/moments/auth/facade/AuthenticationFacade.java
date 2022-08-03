package com.factoria.moments.auth.facade;

import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IAuthRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{

    IAuthRepository authRepository;

    public AuthenticationFacade(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    @Override
    public User getAuthUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
//        var auth =  authRepository.findByUsername(username);
//        if(auth.isEmpty()) throw new NotFoundException("User Not Found", "M-404");
        return authRepository.findByUsername(username).get();
    }
}
