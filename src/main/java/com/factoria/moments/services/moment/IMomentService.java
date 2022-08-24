package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.models.Moment;

import java.io.IOException;
import java.util.List;

public interface IMomentService {

    Moment momentValidation(Long id);
    List<MomentResDto> findAll();

    MomentResDto findById(Long id);

    MomentResDto create(MomentReqDto moment);

    MomentResDto update(MomentReqDto momentReqDto, Long id) throws IOException;

//    MomentResDto like(Long id, User auth);
//
//    MomentResDto save(Long id, User auth);

    MomentResDto delete(Long id) throws IOException;


    List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search);

    List<MomentResDto> getUserMoments(Long id);

    List<MomentResDto> getUserFavMoments();

    List<MomentResDto> getUserSavedMoments();
}
