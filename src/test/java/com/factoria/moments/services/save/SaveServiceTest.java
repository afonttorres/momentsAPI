package com.factoria.moments.services.save;

import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.exceptions.BadRequestException;
import com.factoria.moments.exceptions.NotFoundException;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.Moment;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;
import com.factoria.moments.repositories.IMomentsRepository;
import com.factoria.moments.repositories.ISavesRepository;
import com.factoria.moments.repositories.IUserRepository;
import com.factoria.moments.services.moment.MomentService;
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
class SaveServiceTest {

    @Mock
    ISavesRepository savesRepository;
    @Mock
    IMomentsRepository momentsRepository;
    @Mock
    IUserRepository userRepository;

    @Test
    void getAllShouldReturnListOfSaves() {
        int n = 5;
        var saveService = new SaveService(savesRepository, momentsRepository, userRepository);
        List<Save> saves = this.createSaves(n);
        Mockito.when(savesRepository.findAll()).thenReturn(saves);
        var sut = saveService.getAll();
        assertThat(sut.size(), equalTo(n));
    }

    @Test
    void getMomentSavesShouldReturnListOfSavesMappedByMoment() {
        int n = 3;
        var saveService = new SaveService(savesRepository, momentsRepository, userRepository);
        List<Save> saves = this.createSaves(n);
        Mockito.when(savesRepository.findAll()).thenReturn(saves);
        var sut = saveService.getAll();
        assertThat(sut.size(), equalTo(n));;
    }

    @Test
    void toggleSaveShouldAddSaveToMoment() {
        var saveService = new SaveService(savesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var saver = new User();
        saver.setId(2L);
        var req = new SaveReqDto(1L, 2L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        var sut = saveService.toggleSave(req, saver);
        assertThat(sut, equalTo(true));
    }

    @Test
    void toggleSaveShouldRemoveSaveFromMoment(){
        var saveService = new SaveService(savesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var saver = new User();
        saver.setId(2L);
        var req = new SaveReqDto(1L, 2L);
        var saves =  List.of(new Save(saver, moment));
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Mockito.when(savesRepository.findByMomentId(any(Long.class))).thenReturn(saves);
        saveService.toggleSave(req, saver);
        var sut = saveService.toggleSave(req, saver);
        assertThat(sut, equalTo(false));
    }

    @Test
    void toggleSaveShouldReturnNotFoundExIfMomentDoesNotExist(){
        var saveService = new SaveService(savesRepository, momentsRepository, userRepository);
        var saver = new User();
        saver.setId(2L);
        var req = new SaveReqDto(2L, 2L);
        Exception ex = assertThrows(NotFoundException.class, ()->{
           saveService.toggleSave(req, saver);
        });
        var res = "Moment Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    @Test
    void toggleSaveShouldReturnNotFoundExceptionIfUserDoesNotExist(){
        var saveService = new SaveService(savesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var req = new SaveReqDto(1L, 2L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Exception ex = assertThrows(NotFoundException.class, ()->{
            saveService.toggleSave(req, null);
        });
        var res = "User Not Found";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    @Test
    void toggleSaveShouldBadRequestExceptionIfSaverEqualsMomentCreator(){
        var saveService = new SaveService(savesRepository, momentsRepository, userRepository);
        var moment = this.createMoment();
        var saver = moment.getCreator();
        var req = new SaveReqDto(1L, 1L);
        Mockito.when(momentsRepository.findById(any(Long.class))).thenReturn(Optional.of(moment));
        Exception ex = assertThrows(BadRequestException.class, ()->{
            saveService.toggleSave(req, saver);
        });
        var res = "Moment creator can't save its own moment";
        var sut = ex.getMessage();
        assertTrue(sut.equals(res));
    }

    private List<Save> createSaves(int n){
        var moment = this.createMoment();
        List<Save> saves = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            var user = new User();
            user.setId(Long.valueOf(i));
            var save = new Save(user, moment);
            saves.add(save);
        }
        return saves;
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