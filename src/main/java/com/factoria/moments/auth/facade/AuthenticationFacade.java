package com.factoria.moments.auth.facade;

import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IAuthRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacade implements IAuthenticationFacade{

    IAuthRepository authRepository;

    public AuthenticationFacade(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }


    @Override
    public Optional<User> getAuthUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return authRepository.findByUsername(username);
    }
}
