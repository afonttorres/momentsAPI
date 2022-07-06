package com.factoria.moments.services;

import com.factoria.moments.models.User;

public interface IUserService{
    User findById(Long id);
}
