package com.factoria.moments.services;

import com.factoria.moments.dtos.MomentReqDto;
import com.factoria.moments.dtos.MomentResDto;
import com.factoria.moments.models.User;

import java.util.List;

public interface IMomentService {
    List<MomentResDto> findAll();

    MomentResDto findById(Long id);

    MomentResDto create(MomentReqDto moment, User auth);

    MomentResDto update(MomentReqDto momentReqDto, Long id, User auth);

    MomentResDto like(Long id, User auth);

    MomentResDto save(Long id, User auth);

    MomentResDto delete(Long id, User auth);

    List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search);
}
