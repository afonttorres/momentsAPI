package com.factoria.moments.services.save;

import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.mappers.SaveMapper;
import com.factoria.moments.models.Save;
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
    public String toggleSave(SaveReqDto req) {
        var moment = momentsRepository.findById(req.getMomentId());
        var saver = userRepository.findById(req.getSaverId());
        if(moment.isEmpty() || saver.isEmpty()) return "Incorrect request";
        if(moment.get().getCreator() == saver.get()) return "Moment creator can't like its own moment";
        Save save = new SaveMapper().mapReqToSave(saver.get(), moment.get());
        var found = this.checkIfLikeAlreadyExists(save);
        if(found.isPresent()){
            return this.unsave(found.get());
        }
        return this.save(save);
    }

    private Optional<Save> checkIfLikeAlreadyExists(Save save){
        List<Save> saves = savesRepository.findAll();
        return saves.stream().filter(Like -> Like.getSaver() == save.getSaver()).findAny();
    }

    private String unsave(Save save){
        savesRepository.delete(save);
        return  "User "+save.getSaver().getName()+" unsaved moment with id: "+save.getMoment().getId()+".";
    }

    private String save(Save save){
        savesRepository.save(save);
        return "User "+save.getSaver().getName()+" saved moment with id: "+save.getMoment().getId()+".";
    }
}
