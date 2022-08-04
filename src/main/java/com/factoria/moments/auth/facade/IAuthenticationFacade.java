package com.factoria.moments.auth.facade;

import com.factoria.moments.models.User;

import java.util.Optional;

public interface IAuthenticationFacade {
    public Optional<User> getAuthUser();
}
