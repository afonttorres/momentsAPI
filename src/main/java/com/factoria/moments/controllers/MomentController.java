package com.factoria.moments.controllers;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.dtos.user.request.UserPetitionReqDto;
import com.factoria.moments.models.User;
import com.factoria.moments.services.moment.IMomentService;
import com.factoria.moments.services.user.IUserService;
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
    List<MomentResDto> getAll(@RequestBody UserPetitionReqDto req){
        System.out.println(req);
        User auth = this.getAuth(req.getId());
        return momentService.findAll(auth);
    }

    @GetMapping("/moments/{id}")
    MomentResDto getById(@PathVariable Long id, @RequestBody UserPetitionReqDto req){
        User auth = this.getAuth(req.getId());
        return momentService.findById(id, auth);
    }

    @PostMapping("/moments")
    MomentResDto create(@RequestBody MomentReqDto momentReqDto){
        User auth = this.getAuth(momentReqDto.getUserId());
        return momentService.create(momentReqDto, auth);
    }

    @PutMapping("/moments/{id}")
    MomentResDto update(@RequestBody MomentReqDto momentReqDto, @PathVariable Long id){
        User auth = this.getAuth(momentReqDto.getUserId());
        return momentService.update(momentReqDto,id, auth);
    }

//    @PatchMapping("/moments/{id}/like")
//    MomentResDto like(@PathVariable Long id, @RequestBody UserPetitionReqDto userPetitionReqDto){
//        User auth = this.getAuth(userPetitionReqDto.getId());
//        return momentService.like(id, auth);
//    }
//
//    @PatchMapping("/moments/{id}/save")
//    MomentResDto save(@PathVariable Long id, @RequestBody UserPetitionReqDto userPetitionReqDto){
//        User auth = this.getAuth(userPetitionReqDto.getId());
//        return momentService.save(id, auth);
//    }

    @DeleteMapping("/moments/{id}")
    MomentResDto delete(@PathVariable Long id, @RequestBody UserPetitionReqDto userPetitionReqDto){
        User auth = this.getAuth(userPetitionReqDto.getId());
        return momentService.delete(id, auth);
    }

//    moments?search=${search}
    @GetMapping(value="/moments", params="search")
    List<MomentResDto> getSearch(@RequestParam String search, @RequestBody UserPetitionReqDto req){
        User auth = getAuth(req.getId());
        return momentService.findByDescriptionOrImgUrlOrLocationContaining(search, auth);
    }

    @GetMapping("/users/{id}/moments")
    List<MomentResDto> getUserMoments(@PathVariable Long id, @RequestBody UserPetitionReqDto req){
        User auth = this.getAuth(req.getId());
        return momentService.getUserMoments(id,auth);
    }

}


