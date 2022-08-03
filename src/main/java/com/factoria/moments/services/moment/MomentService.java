package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.MomentMapper;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IMomentsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MomentService implements IMomentService{

    IMomentsRepository momentsRepository;

    public MomentService(IMomentsRepository momentsRepository){
        this.momentsRepository = momentsRepository;
    }

    public Moment momentValidation(Long id){
        var moment = momentsRepository.findById(id);
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        return moment.get();
    }

    @Override
    public List<MomentResDto> findAll(User auth) {
        return new MomentMapper().mapMultipleMomentsToRes( momentsRepository.findAll(), auth);
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
        var moment = momentsRepository.findById(id);
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        if(!moment.get().getCreator().getId().equals(auth.getId())) throw new BadRequestException("Incorrect User", "M-001");
        Moment updatedMoment = new MomentMapper().mapReqToExistingMoment(momentReqDto, moment.get());
        momentsRepository.save(updatedMoment);
        MomentResDto momentRes = new MomentMapper().mapToRes(updatedMoment, auth);
        return momentRes;
    }

    @Override
    public MomentResDto delete(Long id, User auth) {
        var moment = momentsRepository.findById(id);
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        if(!moment.get().getCreator().getId().equals(auth.getId())) throw new BadRequestException("Incorrect User", "M-001");
        MomentResDto resMoment=  new MomentMapper().mapToRes(moment.get(), auth);
        momentsRepository.delete(moment.get());
        return resMoment;
    }

    @Override
    public List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search, User auth) {
        return  new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(search), auth);
    }

    @Override
    public List<MomentResDto> getUserMoments(Long id, User auth) {
        return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findByUserId(id), auth);
    }

    @Override
    public List<MomentResDto> getUserFavMoments(User auth) {
        return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findFavs(auth.getId()), auth);
    }

    @Override
    public List<MomentResDto> getUserSavedMoments(User auth) {
        return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findSaves(auth.getId()),auth);
    }
}
