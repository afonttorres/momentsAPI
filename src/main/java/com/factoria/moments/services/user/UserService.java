package com.factoria.moments.services.user;

import com.factoria.moments.dtos.user.request.UserLogReqDto;
import com.factoria.moments.dtos.user.request.UserPostReqDto;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
import com.factoria.moments.mappers.UserMapper;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService{

    IUserRepository userRepository;

    public UserService(IUserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public User findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<UserNoPassResDto> findAll() {
        List<User> users = userRepository.findAll();
        List<UserNoPassResDto> castedUsers = new ArrayList<>();
        users.forEach(User -> {
            castedUsers.add(new UserMapper().mapUserToNoPassResDto(User));
        });
        return castedUsers;
    }

    @Override
    public UserNoPassResDto getById(Long id) {
        User user = userRepository.findById(id).get();
        return new UserMapper().mapUserToNoPassResDto(user);
    }

    @Override
    public UserNoPassResDto create(UserPostReqDto userPostReqDto) {
        User email = userRepository.findByEmail(userPostReqDto.getEmail());
        if(email != null) return null;
        User username = userRepository.findByUsername(userPostReqDto.getUsername());
        if(username != null) return null;
        User user = new UserMapper().mapPostReqToUser(userPostReqDto);
        return new UserMapper().mapUserToNoPassResDto(userRepository.save(user));
    }

    @Override
    public UserNoPassResDto update(UserUpdateReqDto userUpdateReqDto, Long id) {
        User user = userRepository.findById(id).get();
        System.out.println(user);
        if(user == null) return  null;
        user = new UserMapper().mapPutReqToUser(userUpdateReqDto, user);
        return new UserMapper().mapUserToNoPassResDto(userRepository.save(user));
    }

    @Override
    public UserNoPassResDto log(UserLogReqDto userLogReqDto) {
        User user = userRepository.findByEmail(userLogReqDto.getEmail());
        if(user == null) return null;
        if(!user.getPassword().equals(userLogReqDto.getPassword())) return null;
        return new UserMapper().mapUserToNoPassResDto(user);
    }


}
