package com.factoria.moments.services.user;

import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
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
    }

    @Test
    void getByIdThrowsNotFoundException(){
        var userService = new UserService(userRepository);
        Mockito.when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        Exception ex = assertThrows(NotFoundException.class, ()-> {
            userService.getById(1L);
        });
        var res = "User Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
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
    void createShouldntLetCreateUserIfEmailIsAlreadyInUseThrowBadRequestException(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        User user = this.createUser(id);
        UserPostReqDto req = new UserPostReqDto("email1", "username1", "password1", "name1");
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Exception ex = assertThrows(BadRequestException.class, ()->{
            userService.create(req);
        });
        var res = "Email already in use";
        var sut = ex.getMessage();
        assertThat(sut, equalTo(res));
    }

    @Test
    void createShouldntLetCreateUserIfUsernameIsAlreadyInUseThrowBadRequestException(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        User user = this.createUser(id);
        UserPostReqDto req = new UserPostReqDto("email1", "username1", "password1", "name1");
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(null);
        Mockito.when(userRepository.findByUsername(any(String.class))).thenReturn(user);
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Exception ex = assertThrows(BadRequestException.class, ()->{
            userService.create(req);
        });
        var res = "Username already in use";
        var sut = ex.getMessage();
        assertThat(sut, equalTo(res));
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
    }

    @Test
    void updateShouldntUpdateAUserThatDoesNotExist() {
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        UserUpdateReqDto req = this.createPutReq(id);
        var user = this.createPutReq(1L);
        Exception ex = assertThrows(NotFoundException.class, ()->{
           userService.update(user, id);
        });
        var res = "User Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
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
    }

    @Test
    void logShouldntLetUserLogIfEmailDoesNotExistThrowsException(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        UserLogReqDto req = new UserLogReqDto("email1", "password1");
        Exception ex = assertThrows(NotFoundException.class, ()->{
           userService.log(req);
        });
        var res = "User Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    @Test
    void logShouldntLetUserLogIfPassNotEqualToUserPassThrowsError(){
        Long id = 1L;
        UserService userService = new UserService(userRepository);
        User user = this.createUser(id);
        UserLogReqDto req = new UserLogReqDto("email1", "password2");
        Mockito.when(userRepository.findByEmail(any(String.class))).thenReturn(user);
        Exception ex = assertThrows(BadRequestException.class, ()->{
            userService.log(req);
        });
        var res = "Incorrect Password";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
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