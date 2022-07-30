package com.factoria.moments.mappers;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;

import java.util.ArrayList;
import java.util.List;

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

    public MomentResDto mapToRes(Moment moment, User auth){
        MomentResDto resMoment = new MomentResDto();
        resMoment.setDescription(moment.getDescription());
        resMoment.setLocation(moment.getLocation());
        resMoment.setImgUrl(moment.getImgUrl());
        resMoment.setLikesCount(moment.likesCount());
        resMoment.setSavesCount(moment.savesCount());
        resMoment.setLiked(moment.isLiked(auth));
        resMoment.setSaved(moment.isSaved(auth));
        resMoment.setCommentsCount(moment.commentsCount());
        resMoment.setId(moment.getId());
        resMoment.setCreator(new UserMapper().mapUserToResDtoMoment(moment.getCreator()));
        return  resMoment;
    }

    public List<MomentResDto> mapMultipleMomentsToRes(List<Moment> moments, User auth){
        List<MomentResDto> res = new ArrayList<>();
        moments.forEach(Moment -> res.add(this.mapToRes(Moment, auth)));
        return res;
    }
}
