package com.factoria.moments.services.like;

import com.factoria.moments.dtos.likes.LikeReqDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.LikeMapper;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ILikesRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService implements ILikeService{

    ILikesRepository likesRepository;
    IMomentsRepository momentsRepository;
    IUserRepository userRepository;

    public LikeService(ILikesRepository likesRepository, IMomentsRepository momentsRepository, IUserRepository userRepository){
        this.likesRepository = likesRepository;
        this.momentsRepository = momentsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Like> getAll() {
        return likesRepository.findAll();
    }

    @Override
    public List<Like> getMomentLikes(Long id) {
        return likesRepository.findByMomentId(id);
    }

    @Override
    public boolean toggleLike(LikeReqDto req, User auth) {
        var moment = momentsRepository.findById(req.getMomentId());
        var liker = auth;
        if(moment.isEmpty()) throw new NotFoundException("Moment Not Found", "M-404");
        if(liker == null) throw new NotFoundException("User Not Found", "U-404");
        if(moment.get().getCreator() == liker) throw new BadRequestException("Moment creator can't like its own moment", "M-004"); ;
        Like like = new LikeMapper().mapReqToLike(liker, moment.get());
        var found = this.checkIfLikeAlreadyExists(like);
        if(found.isPresent()){
            return this.dislike(found.get());
        }
        return this.like(like);
    }

    private Optional<Like> checkIfLikeAlreadyExists(Like like){
        List<Like> likes = likesRepository.findByMomentId(like.getMoment().getId());
        System.out.println(likes.size());
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
