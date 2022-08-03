package com.factoria.moments.mappers;

import com.factoria.moments.dtos.user.request.UserUpdateReqDto;
import com.factoria.moments.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @Test
    void mapUserToResDtoMoment() {
        User user = this.createUser();
        var sut = new UserMapper().mapUserToResDtoMoment(user);
        assertEquals(user.getUsername(), sut.getUsername());
        assertEquals(user.getId(), sut.getId());
    }

    @Test
    void mapUserToNoPassResDto() {
        User user = this.createUser();
        var sut = new UserMapper().mapUserToNoPassResDto(user);
        assertEquals(user.getDescription(), sut.getDescription());
        assertEquals(user.getEmail(), sut.getEmail());
        assertEquals(user.getUsername(), sut.getUsername());
    }

    @Test
    void mapPostReqToUser() {
        UserPostReqDto req = new UserPostReqDto("email", "username", "pass", "name");
        var sut = new UserMapper().mapPostReqToUser(req);
        assertEquals(req.getEmail(), sut.getEmail());
        assertEquals(req.getPassword(), sut.getPassword());
        assertEquals(req.getUsername(), sut.getUsername());
    }

    @Test
    void mapPutReqToUser() {
        var resUser = this.createUser();
        resUser.setUsername("username test");

        var user = this.createUser();
        var req = this.createPutReq();
        var sut = new UserMapper().mapPutReqToUser(req, user);
        assertEquals(resUser.getId(), sut.getId());
        assertEquals(resUser.getUsername(), sut.getUsername());
    }

    public User createUser(){
        User user = new User();
        user.setId(1L);
        user.setUsername("username");
        user.setAvatarUrl("avatar");
        user.setEmail("email");
        user.setName("name");
        user.setPassword("pass");
        user.setBannerUrl("banner");
        user.setFollowers(1L);
        user.setFollowing(3L);
        user.setDescription("desc");
        return user;
    }

    public UserUpdateReqDto createPutReq(){
        var req = UserUpdateReqDto.builder()
                .username("username test")
                .name("name")
                .avatarUrl("avatar")
                .description("desc")
                .bannerUrl("banner")
                .email("email")
                .following(2L)
                .followers(2L)
                .build();
        return req;
    }
}