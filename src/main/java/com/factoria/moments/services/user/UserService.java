package com.factoria.moments.services.user;

import com.factoria.moments.dtos.user.request.UserLogReqDto;
import com.factoria.moments.dtos.user.request.UserPostReqDto;
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
import java.util.stream.Collectors;

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
        var users = this.userRepository.findAll();
        if(users.size() <= 1) users = new ArrayList<>();
        if(users.size() > 1){
          users = users.stream().filter(User -> !User.getUsername().equals("admin")).collect(Collectors.toList());
        }
        return new UserMapper().mapMultipleUsersToNoPassResDto( users);
    }

    @Override
    public UserNoPassResDto getById(Long id) {
        if(id == 1) throw new BadRequestException("Wrong input", "U-001");
        var user = userRepository.findById(id);
        if(user.isEmpty()) throw new NotFoundException("User Not Found", "U-404");
        return new UserMapper().mapUserToNoPassResDto(user.get());
    }

    @Override
    public UserNoPassResDto create(UserPostReqDto userPostReqDto) {
        var foundByEmail = userRepository.findByEmail(userPostReqDto.getEmail());
        if(foundByEmail != null) throw new BadRequestException("Email already in use", "U-001");
        var foundByUsername = userRepository.findByUsername(userPostReqDto.getUsername());
        if(foundByUsername != null) throw new BadRequestException("Username already in use", "U-002");
        User user = new UserMapper().mapPostReqToUser(userPostReqDto);
        return new UserMapper().mapUserToNoPassResDto(userRepository.save(user));
    }

    @Override
    public UserNoPassResDto update(UserUpdateReqDto userUpdateReqDto, Long id) {
        var found = userRepository.findById(id);
        if(found.isEmpty()) throw new NotFoundException("User Not Found", "U-404");
        var user = new UserMapper().mapPutReqToUser(userUpdateReqDto, found.get());
        return new UserMapper().mapUserToNoPassResDto(userRepository.save(user));
    }

    @Override
    public UserNoPassResDto log(UserLogReqDto userLogReqDto) {
        User user = userRepository.findByEmail(userLogReqDto.getEmail());
        if(user == null) throw new NotFoundException("User Not Found", "U-404");
        if(!user.getPassword().equals(userLogReqDto.getPassword())) throw new BadRequestException("Incorrect Password", "U-003");
        return new UserMapper().mapUserToNoPassResDto(user);
    }


}
