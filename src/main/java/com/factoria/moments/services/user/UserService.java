package com.factoria.moments.services.user;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.UserMapper;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService{

    IUserRepository userRepository;
    IAuthenticationFacade authenticationFacade;

    public UserService(IUserRepository userRepository, IAuthenticationFacade authenticationFacade){
        this.userRepository = userRepository;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public List<UserNoPassResDto> findAll() {
        var users = this.userRepository.findAll();
        var auth = this.authenticationFacade.getAuthUser();
        users = users.stream()
                .filter(User -> auth.isPresent() ? !User.getUsername().equals("admin") && User != auth.get() : !User.getUsername().equals("admin"))
                .collect(Collectors.toList());
        if (users.size() == 0) users = new ArrayList<>();
        return new UserMapper().mapMultipleUsersToNoPassResDto( users);
    }

    @Override
    public UserNoPassResDto getById(Long id) {
        var user = userRepository.findById(id);
        if(user.get().getUsername().equals("admin")) throw new NotFoundException("User Not Found", "U-404");
        if(user.isEmpty()) throw new NotFoundException("User Not Found", "U-404");
        return new UserMapper().mapUserToNoPassResDto(user.get());
    }

    @Override
    public UserNoPassResDto update(UserUpdateReqDto userUpdateReqDto, Long id) {
        Optional<User> auth = authenticationFacade.getAuthUser();
        if(auth.get().getId() != id) throw new BadRequestException("Incorrect User", "U-002");
        var user = new UserMapper().mapPutReqToUser(userUpdateReqDto, auth.get());
        return new UserMapper().mapUserToNoPassResDto(userRepository.save(user));
    }

}
