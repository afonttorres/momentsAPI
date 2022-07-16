package com.factoria.moments.services.save;

import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.Save;

import java.util.List;

public interface ISaveService {
    List<Save> getAll();
    List<Save> getMomentSaves(Long id);


    String toggleSave(SaveReqDto save);
}
