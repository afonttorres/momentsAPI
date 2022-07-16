package com.factoria.moments.mappers;

import com.factoria.moments.models.Moment;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;

public class SaveMapper {
    public Save mapReqToSave(User saver, Moment moment){
        Save save = new Save();
        save.setSaver(saver);
        save.setMoment(moment);
        return save;
    }
}
