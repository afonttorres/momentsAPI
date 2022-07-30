package com.factoria.moments.mappers;

import com.factoria.moments.dtos.saves.SaveResDto;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;

import java.util.ArrayList;
import java.util.List;

public class SaveMapper {
    public Save mapReqToSave(User saver, Moment moment){
        Save save = new Save();
        save.setSaver(saver);
        save.setMoment(moment);
        return save;
    }

    public SaveResDto mapSaveToRes(Save save){
        return new SaveResDto(save.getId(), save.getMoment().getId(), save.getSaver().getId());
    }

    public List<SaveResDto> mapMultipleSavesToRes(List<Save> saves){
        List<SaveResDto> res = new ArrayList<>();
        saves.forEach(Save -> res.add(this.mapSaveToRes(Save)));
        return res;
    }
}
