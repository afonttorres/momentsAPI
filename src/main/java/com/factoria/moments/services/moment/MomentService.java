package com.factoria.moments.services.moment;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
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
    IAuthenticationFacade authenticationFacade;

    public MomentService(IMomentsRepository momentsRepository, IAuthenticationFacade authenticationFacade){
        this.momentsRepository = momentsRepository;
        this.authenticationFacade = authenticationFacade;
    }

    private User getAuth(){
        Optional<User> auth = authenticationFacade.getAuthUser();
        if(auth.isEmpty()) throw new NotFoundException("User Not Found", "U-404");
        return auth.get();
    }

    public Moment momentValidation(Long id){
        var moment = momentsRepository.findById(id);
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        return moment.get();
    }

    @Override
    public List<MomentResDto> findAll() {
        var auth = authenticationFacade.getAuthUser();
        if(auth.isEmpty()) return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findAll());
        return new MomentMapper().mapMultipleMomentsToRes( momentsRepository.findAll(), auth.get());
    }

    public MomentResDto findById(Long id) {
        Optional<Moment> foundMoment = momentsRepository.findById(id);
        var auth = authenticationFacade.getAuthUser();
        if(foundMoment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        if(auth.isEmpty()) return new MomentMapper().mapToRes(foundMoment.get());
        return new MomentMapper().mapToRes(foundMoment.get(), auth.get());
    }

    @Override
    public MomentResDto create(MomentReqDto reqMoment) {
        var auth = this.getAuth();
        var moment = new MomentMapper().mapReqToMoment(reqMoment, auth);
        momentsRepository.save(moment);
        MomentResDto momentRes =new MomentMapper().mapToRes(moment, auth);
        return momentRes;
    }

    @Override
    public MomentResDto update(MomentReqDto momentReqDto, Long id) {
        var auth = this.getAuth();
        var moment = momentsRepository.findById(id);
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        if(!moment.get().getCreator().getId().equals(auth.getId())) throw new BadRequestException("Incorrect User", "M-001");
        Moment updatedMoment = new MomentMapper().mapReqToExistingMoment(momentReqDto, moment.get());
        momentsRepository.save(updatedMoment);
        MomentResDto momentRes = new MomentMapper().mapToRes(updatedMoment, auth);
        return momentRes;
    }

    @Override
    public MomentResDto delete(Long id) {
        var auth = this.getAuth();
        var moment = momentsRepository.findById(id);
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        if(!moment.get().getCreator().getId().equals(auth.getId())) throw new BadRequestException("Incorrect User", "M-001");
        MomentResDto resMoment=  new MomentMapper().mapToRes(moment.get(), auth);
        momentsRepository.delete(moment.get());
        return resMoment;
    }

    @Override
    public List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search) {
        var auth = this.getAuth();
        return  new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(search), auth);
    }

    @Override
    public List<MomentResDto> getUserMoments(Long id) {
        var auth = authenticationFacade.getAuthUser();
        if(auth.isEmpty()) return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findByUserId(id));
        return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findByUserId(id), auth.get());
    }

    @Override
    public List<MomentResDto> getUserFavMoments() {
        var auth = this.getAuth();
        return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findFavs(auth.getId()), auth);
    }

    @Override
    public List<MomentResDto> getUserSavedMoments() {
        var auth = this.getAuth();
        return new MomentMapper().mapMultipleMomentsToRes(momentsRepository.findSaves(auth.getId()),auth);
    }
}
