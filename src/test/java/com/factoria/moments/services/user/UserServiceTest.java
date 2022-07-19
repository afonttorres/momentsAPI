package com.factoria.moments.services.user;

import com.factoria.moments.dtos.user.request.UserLogReqDto;
import com.factoria.moments.dtos.user.request.UserPostReqDto;
import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.mappers.UserMapper;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserServiceTest {

    @Mock
    IUserRepository userRepository;


    @Test
    void findAllShouldReturnAllUsers() {
        UserService userService = new UserService(userRepository);
        List<User> users = this.createUsers();
        System.out.println(users);
        Mockito.when(userRepository.findAll()).thenReturn(users);
        var sut = userService.findAll();
        assertThat(sut.size(), equalTo(users.size()));
    }

    @Test
    void findByIdShouldReturnUserFromPamId() {
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        User user = this.createUser(id);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(user));
        var sut = userService.findById(id);
        assertThat(sut.getUsername(), equalTo(user.getUsername()));
//        assertThat(sut.getUsername(), equalTo("patata"));
    }

    @Test
    void getByIdShouldReturnUserWithoutPassFromPamId() {
        Long id = 1L;
        User user = this.createUser(id);
        UserService userService = new UserService(userRepository);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(user));
        var sut = userService.getById(id);
        assertThat(sut.getUsername(), equalTo(user.getUsername()));
//        assertThat(sut.getUsername(), equalTo("patata"));
    }

    @Test
    void createShouldPostAUser() {
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        User user = this.createUser(id);
        UserPostReqDto req = new UserPostReqDto("email1", "username1", "password1", "name1");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        var sut = userService.create(req);
        assertThat(sut.getUsername(), equalTo(user.getUsername()));
    }

    @Test
    void createShouldntLetCreateUserIfEmailIsAlreadyInUse(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        User user = this.createUser(id);
        UserPostReqDto req = new UserPostReqDto("email1", "username1", "password1", "name1");
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        var sut = userService.create(req);
        assertThat(sut, equalTo(null)); //create returns null if email exists already
    }

    @Test
    void createShouldntLetCreateUserIfUsernameIsAlreadyInUse(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        User user = this.createUser(id);
        UserPostReqDto req = new UserPostReqDto("email1", "username1", "password1", "name1");
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        var sut = userService.create(req);
        assertThat(sut, equalTo(null)); //create returns null if username exists already
    }

    @Test
    void updateShouldUpdateAUser() {
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        UserUpdateReqDto req = this.createPutReq(id);
        User user = this.createUser(id);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        var sut = userService.update(req, id);
        assertThat(sut.getUsername(), equalTo(req.getUsername()));
        assertThat(sut.getDescription(), equalTo(req.getDescription()));
//        assertThat(sut.getUsername(), equalTo("patata"));
    }

    @Test
    void updateShouldntUpdateAUserThatDoesNotExist() {
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        UserUpdateReqDto req = this.createPutReq(id);
        User user = this.createUser(id);
        /*var sut = userService.update(req, id);
        assertThat(sut.getEmail(), equalTo(null));*/
    }

    @Test
    void logShouldLetUserLog() {
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        UserLogReqDto req = new UserLogReqDto("email1", "password1");
        User user = this.createUser(id);
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        var sut = userService.log(req);
        assertThat(sut.getEmail(), equalTo(req.getEmail()));
//        assertThat(sut.getEmail(), equalTo("patata"));
    }

    @Test
    void logShouldntLetUserLogIfEmailDoesNotExist(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        UserLogReqDto req = new UserLogReqDto("email1", "password1");
        var sut = userService.log(req);
        assertThat(sut, equalTo(null)); //if log doesnt find user by his email returns null
    }

    @Test
    void logShouldntLetUserLogIfPassNotEqualToUserPass(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        UserLogReqDto req = new UserLogReqDto("email1", "password2");
        User user = this.createUser(id); //password1
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        var sut = userService.log(req);
        assertThat(sut, equalTo(null)); //if req pass != user pass log returns null
//        assertThat(sut, equalTo(null)); //fails when change req pass to password1
    }





    public List<User> createUsers(){
        List<User> users = new ArrayList<>();
        for(var i = 0; i<5; i++){
            User user = this.createUser(Long.valueOf(i+1));
            users.add(user);
        }
        return users;
    }

    public User createUser(Long id){
        User user = new User();
        user.setId(id);
        user.setUsername("username"+id);
        user.setAvatarUrl("avatar"+id);
        user.setEmail("email"+id);
        user.setName("name"+id);
        user.setPassword("password"+id);
        user.setBannerUrl("banner"+id);
        user.setFollowers(2);
        user.setFollowing(2);
        user.setDescription("description"+id);
        return user;
    }

    public UserUpdateReqDto createPutReq(Long id){
        var req = UserUpdateReqDto.builder()
                .username("username"+id)
                .name("name"+id)
                .avatarUrl("avatar"+id)
                .description("description"+id)
                .bannerUrl("banner"+id)
                .email("email"+id)
                .following(2)
                .followers(2)
                .build();
        return req;
    }
}