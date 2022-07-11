package com.factoria.moments.services.moment;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.dtos.user.response.UserResDtoMoment;
import com.factoria.moments.mappers.MomentMapper;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IMomentsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
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
        var momentList = List.of(new Moment(), new Moment(), new Moment());
        momentList.forEach(Moment -> Moment.setCreator(user));
        Mockito.when(momentsRepository.findAll()).thenReturn(momentList);
        var sut = momentService.findAll();
        assertThat(sut.size(), equalTo(3));
    }

    @Test
    void findByIdShouldReturnMomentWithPamId() {
        var momentService = new MomentService(momentsRepository);
        var moment = this.createMoment();
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        var sut = momentService.findById(1L);
        assertThat(sut.getDescription(),  equalTo(moment.getDescription()));
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
    void userCantUpdateMomentIfNotHis(){
        var momentService = new MomentService(momentsRepository);
        var req = new MomentReqDto("img1", "desc1", "loc1", 1L);
        Long id = 1L;
        Moment moment = this.createMoment();
        var user = new User();
        user.setId(2L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);
        var sut = momentService.update(req, id, user);
        assertThat(sut, equalTo(null));
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
    void userCantDeleteMomentIfNotHis() {
        Long deletedId = 1L;
        var momentService = new MomentService(momentsRepository);
        Moment moment = this.createMoment();
        var user = new User();
        user.setId(2L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        var sut = momentService.delete(deletedId, user);
        assertThat(sut, equalTo(null));
    }

    @Test
    void likeShouldToggleMomentLiked() {
        var momentService = new MomentService(momentsRepository);
        Long id = 1L;
        var user = new User();
        user.setId(2L);
        Moment moment = this.createMoment();
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);
        var sut = momentService.like(id, user);
        assertThat(sut.isLiked(), equalTo(true));
    }

    @Test
    void creatorCantLikeHisOwnMoment(){
        var momentService = new MomentService(momentsRepository);
        Long id = 1L;
        var user = new User();
        user.setId(2L);
        Moment moment = this.createMoment();
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);
        var sut = momentService.like(id, moment.getCreator());
        assertThat(sut, equalTo(null));
    }

    @Test
    void saveShouldToggleMomentSaved() {
        var momentService = new MomentService(momentsRepository);
        Long id = 1L;
        var user = new User();
        user.setId(2L);
        Moment moment = this.createMoment();
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);
        var sut = momentService.save(id, user);
        assertThat(sut.isSaved(), equalTo(true));
    }

    @Test
    void creatorCantSaveHisOwnMoment(){
        var momentService = new MomentService(momentsRepository);
        Long id = 1L;
        Moment moment = this.createMoment();
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(momentsRepository.save(any(Moment.class))).thenReturn(moment);
        var sut = momentService.save(id, moment.getCreator());
        assertThat(sut, equalTo(null));
    }

    @Test
    void findByDescriptionOrImgUrlOrLocationContainingShouldReturnResMomentList() {
        var momentService = new MomentService(momentsRepository);
        Moment moment = this.createMoment();
        MomentResDto res = new MomentMapper().mapToRes(moment);
        var filtered = List.of(moment);
        var foundMoments = List.of(res);
        Mockito.when(momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(any(String.class))).thenReturn(filtered);
        var sut = momentService.findByDescriptionOrImgUrlOrLocationContaining("desc");
        assertThat(sut, equalTo(foundMoments));
    }

    @Test
    void getUserMomentsShouldReturnResMomentList() {
        var momentService = new MomentService(momentsRepository);
        Moment moment = this.createMoment();
        MomentResDto res = new MomentMapper().mapToRes(moment);
        var filtered = List.of(moment);
        var foundMoments = List.of(res);
        Mockito.when(momentsRepository.findByUserId(any(Long.class))).thenReturn(filtered);
        var sut = momentService.getUserMoments(1L);
        assertThat(sut, equalTo(foundMoments));

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