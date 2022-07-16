package com.factoria.moments.mappers;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MomentMapperTest {

    @Test
    void mapReqToMoment() {
        var req = MomentReqDto.builder()
                .imgUrl("img")
                .description("desc")
                .location("loc")
                .build();
        var user = new User();
        var sut = new MomentMapper().mapReqToMoment(req, user);
        assertEquals(sut.getDescription(), req.getDescription());
    }

    @Test
    void mapReqToExistingMoment(){
        var req = new MomentReqDto("img", "desc", "loc", 1L);
        var moment = this.createMoment();
        var sut = new MomentMapper().mapReqToExistingMoment(req, moment);
        assertEquals(sut.getDescription(), moment.getDescription());
        assertEquals(sut.getCreator().getId(), req.getUserId());
    }

    @Test
    void mapToRes(){
        var moment = createMoment();
        var auth = new User();
        var sut = new MomentMapper().mapToRes(moment, auth);
        assertEquals(sut.getImgUrl(), moment.getImgUrl());
        assertEquals(sut.getCreator().getAvatarUrl(), moment.getCreator().getAvatarUrl());
        assertEquals(sut.getCreator().getId(), moment.getCreator().getId());
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