package com.factoria.moments.services.like;

import com.factoria.moments.dtos.likes.LikeDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.ILikesRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.IUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class LikeServiceTest {

    @Mock
    ILikesRepository likesRepository;
    @Mock
    IMomentsRepository momentsRepository;
    @Mock
    IUserRepository userRepository;

    @Test
    void getAllShouldReturnListOfLikes() {
        int n = 5;
        var likeService = new LikeService(likesRepository, momentsRepository, userRepository);
        List<Like> likes = this.createLikes(n);
        Mockito.when(likesRepository.findAll()).thenReturn(likes);
        var sut = likeService.getAll();
        assertThat(sut.size(), equalTo(n));
    }

    @Test
    void getMomentLikesShouldReturnListOfLikesMappedByMoment() {
        int n = 3;
        var likeService = new LikeService(likesRepository, momentsRepository, userRepository);
        List<Like> likes = this.createLikes(n);
        Mockito.when(likesRepository.findAll()).thenReturn(likes);
        var sut = likeService.getAll();
        assertThat(sut.size(), equalTo(n));;
    }

    @Test
    void toggleLikeShouldAddLikeToMoment() {
        var likeService = new LikeService(likesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var liker = new User();
        liker.setId(2L);
        var req = new LikeDto(1L, 2L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        var sut = likeService.toggleLike(req, liker);
        assertThat(sut, equalTo(true));
    }

    @Test
    void toggleLikeShouldRemoveLikeFromMoment(){
        var likeService = new LikeService(likesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var liker = new User();
        liker.setId(2L);
        var req = new LikeDto(1L, 2L);
        var likes =  List.of(new Like(liker, moment));
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(likesRepository.findByMomentId(any(Long.class))).thenReturn(likes);
        likeService.toggleLike(req, liker);
        var sut = likeService.toggleLike(req, liker);
        assertThat(sut, equalTo(false));
    }

    @Test
    void toggleLikeShouldReturnNotFoundExIfMomentDoesNotExist(){
        var likeService = new LikeService(likesRepository, momentsRepository, userRepository);
        var liker = new User();
        liker.setId(2L);
        var req = new LikeDto(2L, 2L);
        Exception ex = assertThrows(NotFoundException.class, ()->{
            likeService.toggleLike(req, liker);
        });
        var res = "Moment Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    @Test
    void toggleLikeShouldReturnNotFoundExceptionIfUserDoesNotExist(){
        var likeService = new LikeService(likesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var req = new LikeDto(1L, 2L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Exception ex = assertThrows(NotFoundException.class, ()->{
            likeService.toggleLike(req, null);
        });
        var res = "User Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    @Test
    void toggleLikeShouldBadRequestExceptionIfLikerEqualsMomentCreator(){
        var likeService = new LikeService(likesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var liker = moment.getCreator();
        var req = new LikeDto(1L, 1L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Exception ex = assertThrows(BadRequestException.class, ()->{
            likeService.toggleLike(req, liker);
        });
        var res = "Moment creator can't like its own moment";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    private List<Like> createLikes(int n){
        var moment = this.createMoment();
        List<Like> likes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            var user = new User();
            user.setId(Long.valueOf(i));
            var like = new Like(user, moment);
            likes.add(like);
        }
        return likes;
    }

    private Moment createMoment (){
        var creator = new User();
        creator.setId(1L);
        var moment = new Moment();
        moment.setImgUrl("img");
        moment.setDescription("desc");
        moment.setLocation("loc");
        moment.setId(1L);
        moment.setCreator(creator);
        return  moment;
    }
}