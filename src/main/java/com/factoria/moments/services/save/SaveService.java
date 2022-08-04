package com.factoria.moments.services.save;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.dtos.saves.SaveResDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.SaveMapper;
import com.factoria.moments.models.Save;
import com.factoria.moments.repositories.ISavesRepository;
import com.factoria.moments.services.moment.IMomentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaveService implements ISaveService{

    ISavesRepository savesRepository;
    IMomentService momentService;

    IAuthenticationFacade authenticationFacade;

    public SaveService(ISavesRepository savesRepository, IMomentService momentService, IAuthenticationFacade authenticationFacade){
        this.savesRepository = savesRepository;
        this.momentService = momentService;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public List<SaveResDto> getAll() {
        return new SaveMapper().mapMultipleSavesToRes(savesRepository.findAll());
    }

    @Override
    public List<SaveResDto> getMomentSaves(Long id) {
        return new SaveMapper().mapMultipleSavesToRes(savesRepository.findByMomentId(id));
    }

    @Override
    public boolean toggleSave(SaveReqDto req) {
        var moment = momentService.momentValidation(req.getMomentId());
        var saver = authenticationFacade.getAuthUser();
        if(saver.isEmpty()) throw new NotFoundException("User Not Found", "U-404");
        if(moment.getCreator() == saver.get()) throw new BadRequestException("Moment creator can't save its own moment", "M-005");
        Save save = new SaveMapper().mapReqToSave(saver.get(), moment);
        var found = this.checkIfLikeAlreadyExists(save);
        if(found.isPresent()){
            return this.unsave(found.get());
        }
        return this.save(save);
    }

    private Optional<Save> checkIfLikeAlreadyExists(Save save){
        List<Save> saves = savesRepository.findByMomentId(save.getMoment().getId());
        return saves.stream().filter(Like -> Like.getSaver() == save.getSaver()).findAny();
    }

    private boolean unsave(Save save){
        savesRepository.delete(save);
        //return  "User "+save.getSaver().getName()+" unsaved moment with id: "+save.getMoment().getId()+".";
        return false;
    }

    private boolean save(Save save){
        savesRepository.save(save);
        //return "User "+save.getSaver().getName()+" saved moment with id: "+save.getMoment().getId()+".";
        return true;
    }
}
