package com.factoria.moments.mappers;

import com.factoria.moments.dtos.likes.LikeDto;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LikeMapperTest {

    @Test
    void mapReqToLikeShouldMapAReqToALike() {
        var like = new Like();
        var req = LikeDto.builder()
                .likerId(1L)
                .momentId(1L)
                .build();
        var user = this.createUser(1L);
        var moment = this.createMoment();
        like.setMoment(moment);
        like.setLiker(user);

        var sut = new LikeMapper().mapReqToLike(user, moment);
        assertEquals(sut, like);
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

    public Moment createMoment(){
        var user = new User();
        user.setId(1L);
        var moment = new Moment();
        moment.setId(1L);
        moment.setDescription("desc");
        moment.setImgUrl("img");
        moment.setLocation("loc");
        moment.setCreator(user);
        return moment;
    }
}