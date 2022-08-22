package com.factoria.moments.services.like;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
import com.factoria.moments.dtos.likes.LikeResDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.LikeMapper;
import com.factoria.moments.models.Like;
import com.factoria.moments.repositories.ILikesRepository;
import com.factoria.moments.services.moment.IMomentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService implements ILikeService{

    ILikesRepository likesRepository;
    IMomentService momentService;
    IAuthenticationFacade authenticationFacade;

    public LikeService(ILikesRepository likesRepository, IMomentService momentService, IAuthenticationFacade authenticationFacade){
        this.likesRepository = likesRepository;
        this.momentService = momentService;
        this.authenticationFacade = authenticationFacade;
    }

    @Override
    public List<LikeResDto> getAll() {
        return new LikeMapper().mapMultipleLikesToLikeDto(likesRepository.findAll());
    }

    @Override
    public List<LikeResDto> getMomentLikes(Long id) {
        return new LikeMapper().mapMultipleLikesToLikeDto(likesRepository.findByMomentId(id));
    }

    @Override
    public boolean toggleLike(Long id) {
        var moment = momentService.momentValidation(id);
        var liker =authenticationFacade.getAuthUser();
        if(liker.isEmpty()) throw new NotFoundException("User Not Found", "U-404");
        if(moment.getCreator() == liker.get()) throw new BadRequestException("Moment creator can't like its own moment", "M-004"); ;
        Like like = new LikeMapper().mapReqToLike(liker.get(), moment);
        var found = this.checkIfLikeAlreadyExists(like);
        if(found.isPresent()){
            return this.dislike(found.get());
        }
        return this.like(like);
    }

    private Optional<Like> checkIfLikeAlreadyExists(Like like){
        List<Like> likes = likesRepository.findByMomentId(like.getMoment().getId());
        return likes.stream().filter(Like -> Like.getLiker() == like.getLiker()).findAny();
    }

    private boolean dislike(Like like){
        likesRepository.delete(like);
        //return  "User "+like.getLiker().getName()+" disliked moment with id: "+like.getMoment().getId()+".";
        return false;
    }

    private boolean like(Like like){
        likesRepository.save(like);
        //return "User "+like.getLiker().getName()+" liked moment with id: "+like.getMoment().getId()+".";
        return true;
    }




}
