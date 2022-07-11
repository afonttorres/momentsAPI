package com.factoria.moments.mappers;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;

public class MomentMapper {
    public Moment mapReqToMoment(MomentReqDto reqMoment, User creator){
        Moment moment = new Moment();
        moment.setImgUrl(reqMoment.getImgUrl());
        moment.setDescription(reqMoment.getDescription());
        moment.setLocation(reqMoment.getLocation());
        moment.setCreator(creator);
        return moment;
    }

    public Moment mapReqToExistingMoment(MomentReqDto reqMoment, Moment moment){
        moment.setImgUrl(reqMoment.getImgUrl());
        moment.setDescription(reqMoment.getDescription());
        moment.setLocation(reqMoment.getLocation());
        return moment;
    }

    public MomentResDto mapToRes(Moment moment){
        MomentResDto resMoment = new MomentResDto();
        resMoment.setDescription(moment.getDescription());
        resMoment.setLocation(moment.getLocation());
        resMoment.setImgUrl(moment.getImgUrl());
        resMoment.setLiked(moment.isLiked());
        resMoment.setLikes(moment.getLikes());
        resMoment.setSaved(moment.isSaved());
        resMoment.setSaves(moment.getSaves());
        resMoment.setCommentsCount(moment.commentsCount());
        resMoment.setId(moment.getId());
        resMoment.setCreator(new UserMapper().mapUserToResDtoMoment(moment.getCreator()));
        return  resMoment;
    }
}
