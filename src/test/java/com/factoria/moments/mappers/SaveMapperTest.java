package com.factoria.moments.mappers;

import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SaveMapperTest {

    @Test
    void mapReqToSaveShouldMapAReqToASave() {
        var save = new Save();
        var req = SaveReqDto.builder()
                .momentId(1L)
                .saverId(1L)
                .build();
        var user = this.createUser(1L);
        var moment = this.createMoment();
        save.setMoment(moment);
        save.setSaver(user);

        var sut = new SaveMapper().mapReqToSave(user, moment);
        assertEquals(sut, save);
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