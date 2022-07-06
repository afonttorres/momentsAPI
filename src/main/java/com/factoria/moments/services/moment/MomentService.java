package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IMomentsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MomentService implements IMomentService{

    IMomentsRepository momentsRepository;

    public MomentService(IMomentsRepository momentsRepository){
        this.momentsRepository = momentsRepository;
    }
    @Override
    public List<MomentResDto> findAll() {
        List<Moment> moments = momentsRepository.findAll();
        List <MomentResDto> resMoments = new ArrayList<>();
        moments.forEach(Moment -> {
            MomentResDto resMoment = this.castMomentToResMoment(Moment);
            resMoments.add(resMoment);
        });
        return resMoments;
    }

    @Override
    public MomentResDto findById(Long id) {
        Moment foundMoment = momentsRepository.findById(id).get();
        MomentResDto resMoment = this.castMomentToResMoment(foundMoment);
        return resMoment;
    }

    @Override
    public MomentResDto create(MomentReqDto reqMoment, User auth) {
        Moment moment = new Moment();
        moment = this.castReqMomentToMoment(moment, reqMoment, auth);
        momentsRepository.save(moment);
        MomentResDto momentRes = this.castMomentToResMoment(moment);
        return momentRes;
    }

    @Override
    public MomentResDto update(MomentReqDto momentReqDto, Long id, User auth) {
        if(momentReqDto.getUserId() != auth.getId()) return null;
        Moment moment = momentsRepository.findById(id).get();
        moment = this.castReqMomentToMoment(moment, momentReqDto, auth);
        momentsRepository.save(moment);
        MomentResDto momentRes = this.castMomentToResMoment(moment);
        return momentRes;
    }

    @Override
    public MomentResDto like(Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(moment.getCreator().getId() == auth.getId()) return null;
        moment.setLiked(!moment.isLiked());
        momentsRepository.save(moment);
        MomentResDto momentRes = this.castMomentToResMoment(moment);
        return momentRes;
    }

    @Override
    public MomentResDto save(Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(moment.getCreator().getId() == auth.getId()) return null;
        moment.setSaved(!moment.isSaved());
        momentsRepository.save(moment);
        MomentResDto momentRes = this.castMomentToResMoment(moment);
        return momentRes;
    }

    @Override
    public MomentResDto delete(Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(moment.getCreator().getId() != auth.getId()) return null;
        MomentResDto resMoment= this.castMomentToResMoment(moment);
        momentsRepository.delete(moment);
        return resMoment;
    }

    @Override
    public List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search) {
        List<Moment> searchCollection = momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(search);
        List <MomentResDto> resSearchCollection = new ArrayList<>();
        searchCollection.forEach(Moment -> {
            MomentResDto resMoment = this.castMomentToResMoment(Moment);
            resSearchCollection.add(resMoment);
        });
        return resSearchCollection;
    }

    private Moment castReqMomentToMoment(Moment moment, MomentReqDto reqMoment, User auth){
        moment.setImgUrl(reqMoment.getImgUrl());
        moment.setDescription(reqMoment.getDescription());
        moment.setLocation(reqMoment.getLocation());
        moment.setCreator(auth);
        return moment;
    }

    private MomentResDto castMomentToResMoment(Moment moment){
        MomentResDto resDto = new MomentResDto();
        resDto.setDescription(moment.getDescription());
        resDto.setLocation(moment.getLocation());
        resDto.setImgUrl(moment.getImgUrl());
        resDto.setLiked(moment.isLiked());
        resDto.setLikes(moment.getLikes());
        resDto.setSaved(moment.isSaved());
        resDto.setSaves(moment.getSaves());
        resDto.setCommentsCount(moment.commentsCount());
        resDto.setId(moment.getId());
        UserResDtoMoment creator = this.castUserToResUser(moment.getCreator());
        resDto.setCreator(creator);
        return resDto;
    }

    private UserResDtoMoment castUserToResUser(User user){
        UserResDtoMoment resDto = new UserResDtoMoment();
        resDto.setName(user.getName());
        resDto.setUsername(user.getUsername());
        resDto.setAvatarUrl(user.getAvatarUrl());
        resDto.setId(user.getId());
        return resDto;
    }
}
