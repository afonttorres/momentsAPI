package com.factoria.moments.auth.configuration;

import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IAuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService{

    IAuthRepository authRepository;

    @Autowired
    public UserDetailsServiceImpl(IAuthRepository authRepository){
        this.authRepository = authRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + "Not Found"));

        return UserDetailsImpl.build(user);
    }
}
