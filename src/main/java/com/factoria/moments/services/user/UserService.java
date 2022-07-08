package com.factoria.moments.services.user;

import com.factoria.moments.dtos.user.request.UserLogReqDto;
import com.factoria.moments.dtos.user.request.UserPostReqDto;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.dtos.user.response.UserNoPassResDto;
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
            castedUsers.add(castUserToUserNoPassResDto(User));
        });
        return castedUsers;
    }

    @Override
    public UserNoPassResDto create(UserPostReqDto userPostReqDto) {
        User email = userRepository.findByEmail(userPostReqDto.getEmail());
        if(email != null) return null;
        User username = userRepository.findByUsername(userPostReqDto.getUsername());
        if(username != null) return null;
        User user = this.castUserPostReqDtoToUser(userPostReqDto);
        userRepository.save(user);
        return this.castUserToUserNoPassResDto(user);
    }

    @Override
    public UserNoPassResDto update(UserUpdateReqDto userUpdateReqDto, Long id) {
        User user = userRepository.findById(id).get();
        user = this.castUserUpdateReqDtoToUser(userUpdateReqDto, user);
        userRepository.save(user);
        return this.castUserToUserNoPassResDto(user);
    }

    @Override
    public UserNoPassResDto log(UserLogReqDto userLogReqDto) {
        User user = userRepository.findByEmail(userLogReqDto.getEmail());
        if(user == null) return null;
        if(!user.getPassword().equals(userLogReqDto.getPassword())) return null;
        System.out.println("ATTEMPTING TO LOG");
        return this.castUserToUserNoPassResDto(user);
    }

    @Override
    public UserNoPassResDto getById(Long id) {
        User user = userRepository.findById(id).get();
        return this.castUserToUserNoPassResDto(user);
    }

    private UserNoPassResDto castUserToUserNoPassResDto(User user){
        UserNoPassResDto userNoPassResDto = new UserNoPassResDto();
        userNoPassResDto.setDescription(user.getDescription());
        userNoPassResDto.setAvatarUrl(user.getAvatarUrl());
        userNoPassResDto.setBannerUrl(user.getBannerUrl());
        userNoPassResDto.setEmail(user.getEmail());
        userNoPassResDto.setFollowers(user.getFollowers());
        userNoPassResDto.setFollowing(user.getFollowing());
        userNoPassResDto.setId(user.getId());
        userNoPassResDto.setUsername(user.getUsername());
        userNoPassResDto.setName(user.getName());
        return userNoPassResDto;
    }

    private User castUserPostReqDtoToUser(UserPostReqDto userPostReqDto){
        User user = new User();
        user.setEmail(userPostReqDto.getEmail());
        user.setName(userPostReqDto.getName());
        user.setUsername(userPostReqDto.getUsername());
        user.setPassword(userPostReqDto.getPassword());
        System.out.println(user.getEmail());
        return user;
    }

    private User castUserUpdateReqDtoToUser(UserUpdateReqDto userUpdateReqDto, User user){
        user.setDescription(userUpdateReqDto.getDescription());
        user.setAvatarUrl(userUpdateReqDto.getAvatarUrl());
        user.setFollowers(userUpdateReqDto.getFollowers());
        user.setBannerUrl(userUpdateReqDto.getBannerUrl());
        user.setFollowing(userUpdateReqDto.getFollowing());
        user.setUsername(userUpdateReqDto.getUsername());
        user.setEmail(userUpdateReqDto.getEmail());
        user.setName(userUpdateReqDto.getName());
        return user;
    }
}
