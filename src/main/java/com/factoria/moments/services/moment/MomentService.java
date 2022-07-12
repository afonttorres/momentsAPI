package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.mappers.MomentMapper;
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
            MomentResDto resMoment = new MomentMapper().mapToRes(Moment);
            resMoments.add(resMoment);
        });
        return resMoments;
    }

    @Override
    public MomentResDto findById(Long id) {
        Moment foundMoment = momentsRepository.findById(id).get();
        MomentResDto resMoment = new MomentMapper().mapToRes(foundMoment);
        return resMoment;
    }

    @Override
    public MomentResDto create(MomentReqDto reqMoment, User auth) {
        var moment = new MomentMapper().mapReqToMoment(reqMoment, auth);
        momentsRepository.save(moment);
        MomentResDto momentRes =new MomentMapper().mapToRes(moment);
        return momentRes;
    }

    @Override
    public MomentResDto update(MomentReqDto momentReqDto, Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(!moment.getCreator().getId().equals(auth.getId())) return null;
        Moment updatedMoment = new MomentMapper().mapReqToExistingMoment(momentReqDto, moment);
        momentsRepository.save(updatedMoment);
        MomentResDto momentRes = new MomentMapper().mapToRes(updatedMoment);
        return momentRes;
    }

    @Override
    public MomentResDto like(Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(moment.getCreator().getId() == auth.getId()) return null;
        moment.setLiked(!moment.isLiked());
        momentsRepository.save(moment);
        MomentResDto momentRes = new MomentMapper().mapToRes(moment);

        return momentRes;
    }

    @Override
    public MomentResDto save(Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(moment.getCreator().getId() == auth.getId()) return null;
        moment.setSaved(!moment.isSaved());
        momentsRepository.save(moment);
        MomentResDto momentRes =  new MomentMapper().mapToRes(moment);
        return momentRes;
    }

    @Override
    public MomentResDto delete(Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(!moment.getCreator().getId().equals(auth.getId())) return null;
        MomentResDto resMoment=  new MomentMapper().mapToRes(moment);
        momentsRepository.delete(moment);
        return resMoment;
    }

    @Override
    public List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search) {
        List<Moment> searchCollection = momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(search);
        List <MomentResDto> resSearchCollection = new ArrayList<>();
        searchCollection.forEach(Moment -> {
            MomentResDto resMoment = new MomentMapper().mapToRes(Moment);
            resSearchCollection.add(resMoment);
        });
        return resSearchCollection;
    }

    @Override
    public List<MomentResDto> getUserMoments(Long id) {
        List<Moment> userMoments = momentsRepository.findByUserId(id);
        List <MomentResDto> userMomentsRes = new ArrayList<>();
        userMoments.forEach(Moment ->{
            userMomentsRes.add( new MomentMapper().mapToRes(Moment));
        });
        return userMomentsRes;
    }
}
