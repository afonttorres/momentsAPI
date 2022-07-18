package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.MomentMapper;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ICommentRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.services.like.ILikeService;
import com.factoria.moments.services.save.ISaveService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MomentService implements IMomentService{

    IMomentsRepository momentsRepository;

    public MomentService(IMomentsRepository momentsRepository){
        this.momentsRepository = momentsRepository;
    }
    @Override
    public List<MomentResDto> findAll(User auth) {
        List<Moment> moments = momentsRepository.findAll();
        List <MomentResDto> resMoments = new ArrayList<>();
        moments.forEach(Moment -> {
            MomentResDto resMoment = new MomentMapper().mapToRes(Moment, auth);
            resMoments.add(resMoment);
        });
        return resMoments;
    }

    @Override
    public MomentResDto findById(Long id, User auth) {
        Optional<Moment> foundMoment = momentsRepository.findById(id);
        if(foundMoment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        MomentResDto resMoment = new MomentMapper().mapToRes(foundMoment.get(), auth);
        return resMoment;
    }

    @Override
    public MomentResDto create(MomentReqDto reqMoment, User auth) {
        var moment = new MomentMapper().mapReqToMoment(reqMoment, auth);
        momentsRepository.save(moment);
        MomentResDto momentRes =new MomentMapper().mapToRes(moment, auth);
        return momentRes;
    }

    @Override
    public MomentResDto update(MomentReqDto momentReqDto, Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(!moment.getCreator().getId().equals(auth.getId())) return null;
        Moment updatedMoment = new MomentMapper().mapReqToExistingMoment(momentReqDto, moment);
        momentsRepository.save(updatedMoment);
        MomentResDto momentRes = new MomentMapper().mapToRes(updatedMoment, auth);
        return momentRes;
    }

    @Override
    public MomentResDto delete(Long id, User auth) {
        Moment moment = momentsRepository.findById(id).get();
        if(!moment.getCreator().getId().equals(auth.getId())) return null;
        MomentResDto resMoment=  new MomentMapper().mapToRes(moment, auth);
        momentsRepository.delete(moment);
        return resMoment;
    }

    @Override
    public List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search, User auth) {
        List<Moment> searchCollection = momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(search);
        List <MomentResDto> resSearchCollection = new ArrayList<>();
        searchCollection.forEach(Moment -> {
            MomentResDto resMoment = new MomentMapper().mapToRes(Moment, auth);
            resSearchCollection.add(resMoment);
        });
        return resSearchCollection;
    }

    @Override
    public List<MomentResDto> getUserMoments(Long id, User auth) {
        List<Moment> userMoments = momentsRepository.findByUserId(id);
        List <MomentResDto> userMomentsRes = new ArrayList<>();
        userMoments.forEach(Moment ->{
            userMomentsRes.add( new MomentMapper().mapToRes(Moment, auth));
        });
        return userMomentsRes;
    }

    @Override
    public List<MomentResDto> getUserFavMoments(User auth) {
        List<Moment> favMoments = momentsRepository.findFavs(auth.getId());
        List<MomentResDto> favMomentsRes = new ArrayList<>();
        favMoments.forEach(Moment -> {
            favMomentsRes.add(new MomentMapper().mapToRes(Moment, auth));
        });
        return favMomentsRes;
    }

    @Override
    public List<MomentResDto> getUserSavedMoments(User auth) {
        List<Moment> savedMoments = momentsRepository.findSaves(auth.getId());
        List<MomentResDto> savedMomentsRes = new ArrayList<>();
        savedMoments.forEach(Moment -> {
            savedMomentsRes.add(new MomentMapper().mapToRes(Moment, auth));
        });
        return savedMomentsRes;
    }
}
