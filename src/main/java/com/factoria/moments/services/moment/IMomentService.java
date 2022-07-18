package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.models.User;

import java.util.List;

public interface IMomentService {
    List<MomentResDto> findAll(User auth);

    MomentResDto findById(Long id, User auth);

    MomentResDto create(MomentReqDto moment, User auth);

    MomentResDto update(MomentReqDto momentReqDto, Long id, User auth);

//    MomentResDto like(Long id, User auth);
//
//    MomentResDto save(Long id, User auth);

    MomentResDto delete(Long id, User auth);


    List<MomentResDto> findByDescriptionOrImgUrlOrLocationContaining(String search, User auth);

    List<MomentResDto> getUserMoments(Long id, User auth);

    List<MomentResDto> getUserFavMoments(User auth);

    List<MomentResDto> getUserSavedMoments(User auth);
}
