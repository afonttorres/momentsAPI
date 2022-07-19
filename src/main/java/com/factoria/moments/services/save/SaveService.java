package com.factoria.moments.services.save;

import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.SaveMapper;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.ISavesRepository;
import com.factoria.moments.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SaveService implements ISaveService{

    ISavesRepository savesRepository;
    IMomentsRepository momentsRepository;
    IUserRepository userRepository;

    public SaveService(ISavesRepository savesRepository, IMomentsRepository momentsRepository, IUserRepository userRepository){
        this.savesRepository = savesRepository;
        this.momentsRepository = momentsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Save> getAll() {
        return savesRepository.findAll();
    }

    @Override
    public List<Save> getMomentSaves(Long id) {
        return savesRepository.findByMomentId(id);
    }

    @Override
    public boolean toggleSave(SaveReqDto req, User auth) {
        var moment = momentsRepository.findById(req.getMomentId());
        var saver = auth;
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        if(saver == null) throw new NotFoundException("User Not Found", "U-404");
        if(moment.get().getCreator() == saver) throw new BadRequestException("Moment creator can't save its own moment", "M-005");
        Save save = new SaveMapper().mapReqToSave(saver, moment.get());
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
