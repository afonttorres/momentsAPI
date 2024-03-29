package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.mappers.MomentMapper;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IMomentsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest
class MomentServiceTest {

    @Mock
    IMomentsRepository momentsRepository;

    @Test
    void findAllShouldReturnAllMoments() {
        var momentService = new MomentService(momentsRepository);
        var user = new User();
        user.setId(1L);
        var momentList = List.of(new Moment(), new Moment(), new Moment());
        momentList.forEach(Moment -> Moment.setCreator(user));
        Mockito.when(momentsRepository.findAll()).thenReturn(momentList);
        var sut = momentService.findAll(user);
        assertThat(sut.size(), equalTo(3));
    }

    @Test
    void findByIdShouldReturnMomentWithPamId() {
        var momentService = new MomentService(momentsRepository);
        var moment = this.createMoment();
        var user = new User();
        user.setId(1L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        var sut = momentService.momentValidation(1L, user);
        assertThat(sut.getDescription(),  equalTo(moment.getDescription()));
    }

    @Test
    void findByIdThrowsException(){
        var momentService = new MomentService(momentsRepository);
        Exception ex = assertThrows(NotFoundException.class, ()->{
            momentService.momentValidation(1L, new User());
        });
        String resmsg = "Moment Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(resmsg));
    }

    @Test
    void createShoudCreateNewMomentFromReq() {
        var momentService = new MomentService(momentsRepository);
        var req = new MomentReqDto("img", "desc", "loc", 1L);
        var moment = createMoment();
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);
        var sut = momentService.create(req, moment.getCreator());
        assertThat(sut.getDescription(), equalTo(req.getDescription()));
        assertThat(sut.getImgUrl(), equalTo(req.getImgUrl()));
        assertThat(sut.getLocation(), equalTo(req.getLocation()));
        assertThat(sut.getCreator().getId(), equalTo(req.getUserId()));
    }

    @Test
    void updateShouldUpdateMomentFromReq() {
        var momentService = new MomentService(momentsRepository);
        var req = new MomentReqDto("img1", "desc1", "loc1", 1L);
        Long id = 1L;

        var user = new User();
        user.setId(1L);

        Moment moment = this.createMoment();

        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);

        var sut = momentService.update(req, id, user);
        assertThat(sut.getDescription(), equalTo(req.getDescription()));
        assertThat(sut.getImgUrl(), equalTo(req.getImgUrl()));
        assertThat(sut.getLocation(), equalTo(req.getLocation()));
        assertThat(sut.getCreator().getId(), equalTo(req.getUserId()));
    }

    @Test
    void userCantUpdateMomentIfNotHisAndThrowsException(){
        var momentService = new MomentService(momentsRepository);
        var req = new MomentReqDto("img1", "desc1", "loc1", 1L);
        Long id = 1L;
        Moment moment = this.createMoment();
        var user = new User();
        user.setId(2L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);
        Exception exception = assertThrows(BadRequestException.class, ()->{
           momentService.update(req, id, user);
        });
        var res = "Incorrect User";
        var sut = exception.getMessage();
        assertTrue(sut.equals(res));
    }

    @Test
    void updateThrowsNotFoundException(){
        var momentService = new MomentService(momentsRepository);
        var req = new MomentReqDto("img", "desc", "loc", 1L);
        var user = new User();
        user.setId(1L);
        Exception ex = assertThrows(NotFoundException.class, ()->{
            momentService.update(req, 1L, user);
        });
        var resmsg = "Moment Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(resmsg));
    }

    @Test
    void updateThrowsBadRequestException(){
        var momentService = new MomentService(momentsRepository);
        var req = new MomentReqDto("img", "desc", "loc", 1L);
        var user = new User();
        user.setId(2L);
        var moment = createMoment();
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(moment));
        Exception ex = assertThrows(BadRequestException.class, ()->{
            momentService.update(req, 1L, user);
        });
        var resmsg = "Incorrect User";
        var sut = ex.getMessage();
        assertTrue(sut.equals(resmsg));
    }

    @Test
    void deleteShouldReturnDeletedMoment() {
        Long deletedId = 1L;
        var momentService = new MomentService(momentsRepository);
        Moment moment = createMoment();
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        var sut = momentService.delete(deletedId, moment.getCreator());
        assertThat(sut.getDescription(), equalTo(moment.getDescription()));
    }


    @Test
    void deleteThrowsNotFoundExceptionWhenMomentNotFound(){
        var momentService = new MomentService(momentsRepository);
        Exception ex = assertThrows(NotFoundException.class, ()->{
           momentService.delete(1L, new User());
        });
        var res = "Moment Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    @Test
    void deleteThrowsBadRequestWhenIncorrectUser(){
        var momentService = new MomentService(momentsRepository);
        var moment = createMoment();
        Mockito.when(momentsRepository.findById(1L)).thenReturn(Optional.ofNullable(moment));
        Exception ex = assertThrows(BadRequestException.class, ()->{
           momentService.delete(1L, new User());
        });
        var res = "Incorrect User";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }
    @Test
    void findByDescriptionOrImgUrlOrLocationContainingShouldReturnResMomentList() {
        var momentService = new MomentService(momentsRepository);
        Moment moment = this.createMoment();
        var user = new User();
        user.setId(1L);
        MomentResDto res = new MomentMapper().mapToRes(moment, user);
        var filtered = List.of(moment);
        var foundMoments = List.of(res);
        Mockito.when(momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(any(String.class))).thenReturn(filtered);
        var sut = momentService.findByDescriptionOrImgUrlOrLocationContaining("desc", user);
        assertThat(sut, equalTo(foundMoments));
    }

    @Test
    void getUserMomentsShouldReturnResMomentList() {
        var momentService = new MomentService(momentsRepository);
        Moment moment = this.createMoment();
        var user = new User();
        user.setId(1L);
        MomentResDto res = new MomentMapper().mapToRes(moment, user);
        var filtered = List.of(moment);
        var foundMoments = List.of(res);
        Mockito.when(momentsRepository.findByUserId(any(Long.class))).thenReturn(filtered);
        var sut = momentService.getUserMoments(1L, user);
        assertThat(sut, equalTo(foundMoments));

    }

    @Test
    void getUserFavMomentsShouldReturnListOfFavMoments(){
        var momentService = new MomentService(momentsRepository);
        var moment1 = createMoment();
        var moment2 = createMoment();
        var moment3 = createMoment();
        var user = new User();
        user.setId(1L);
        moment2.setId(2L);
        moment3.setId(3L);
        List<Moment> favMoments = List.of(moment1, moment2, moment3);
        List<MomentResDto> res = new ArrayList<>();
        favMoments.forEach(Moment -> res.add(new MomentMapper().mapToRes(Moment, user)));
        Mockito.when(momentsRepository.findFavs(user.getId())).thenReturn(favMoments);
        var sut = momentService.getUserFavMoments(user);
        assertThat(sut, equalTo(res));
    }

    @Test
    void getUserSavedMomentsShouldReturnListOfSavedMoments(){
        var momentService = new MomentService(momentsRepository);
        var moment1 = createMoment();
        var moment2 = createMoment();
        var moment3 = createMoment();
        var user = new User();
        user.setId(1L);
        moment2.setId(2L);
        moment3.setId(3L);
        List<Moment> savedMoments = List.of(moment1, moment2, moment3);
        List<MomentResDto> res = new ArrayList<>();
        savedMoments.forEach(Moment -> res.add(new MomentMapper().mapToRes(Moment, user)));
        Mockito.when(momentsRepository.findSaves(user.getId())).thenReturn(savedMoments);
        var sut = momentService.getUserSavedMoments(user);
        assertThat(sut, equalTo(res));
    }

    public Moment createMoment(){
        var user = new User();
        user.setId(1L);
        var moment = new Moment();
        moment.setId(1L);
        moment.setDescription("desc");
        moment.setImgUrl("img");
        moment.setLocation("loc");
        moment.setCreator(user);
        return moment;
    }
}