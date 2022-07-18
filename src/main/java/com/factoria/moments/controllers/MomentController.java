package com.factoria.moments.controllers;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.dtos.user.request.UserPetitionReqDto;
import com.factoria.moments.models.User;
import com.factoria.moments.services.moment.IMomentService;
import com.factoria.moments.services.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class MomentController {

    IMomentService momentService;
    IUserService userService;

    public MomentController(IMomentService momentService, IUserService userService){
        this.momentService = momentService;
        this.userService = userService;
    }

    private User getAuth(Long id){
        return userService.findById(id);
    }

    @GetMapping("/moments")
    List<MomentResDto> getAll(){
        User auth = this.getAuth(1L);
        return momentService.findAll(auth);
    }

    @GetMapping("/moments/{id}")
    ResponseEntity<MomentResDto> getById(@PathVariable Long id){
        User auth = this.getAuth(1L);
        var moment = momentService.findById(id, auth);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

    @PostMapping("/moments")
    MomentResDto create(@RequestBody MomentReqDto momentReqDto){
        User auth = this.getAuth(1L);
        return momentService.create(momentReqDto, auth);
    }

    @PutMapping("/moments/{id}")
    ResponseEntity<MomentResDto> update(@RequestBody MomentReqDto momentReqDto, @PathVariable Long id){
        User auth = this.getAuth(1L);
        var moment = momentService.update(momentReqDto,id, auth);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

    @DeleteMapping("/moments/{id}")
    ResponseEntity<MomentResDto> delete(@PathVariable Long id){
        User auth = this.getAuth(1L);
        var moment = momentService.delete(id, auth);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

//    moments?search=${search}
    @GetMapping(value="/moments", params="search")
    List<MomentResDto> getSearch(@RequestParam String search){
        User auth = getAuth(1L);
        return momentService.findByDescriptionOrImgUrlOrLocationContaining(search, auth);
    }

    @GetMapping("/users/{id}/moments")
    List<MomentResDto> getUserMoments(@PathVariable Long id){
        User auth = this.getAuth(1L);
        return momentService.getUserMoments(id,auth);
    }

    @GetMapping("/fav-moments")
    List <MomentResDto> getUserFavMoments(){
        User auth = this.getAuth(1L);
        return momentService.getUserFavMoments(auth);
    }

    @GetMapping("/saved-moments")
    List <MomentResDto> getUserSavedMoments(){
        User auth = this.getAuth(1L);
        return momentService.getUserSavedMoments(auth);
    }
}


