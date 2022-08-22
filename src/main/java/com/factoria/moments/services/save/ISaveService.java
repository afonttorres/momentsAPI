package com.factoria.moments.services.save;

import com.factoria.moments.dtos.saves.SaveResDto;

import java.util.List;

public interface ISaveService {
    List<SaveResDto> getAll();
    List<SaveResDto> getMomentSaves(Long id);
    boolean toggleSave(Long id);
}
