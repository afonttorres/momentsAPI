package com.factoria.moments.controllers;

import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.services.moment.IMomentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins="*")
public class MomentController {

    IMomentService momentService;

    public MomentController(IMomentService momentService){
        this.momentService = momentService;
    }


    @GetMapping("/moments")
    ResponseEntity<List<MomentResDto>> getAll(){
        return new ResponseEntity<>(momentService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/moments/{id}")
    ResponseEntity<MomentResDto> getById(@PathVariable Long id){
        var moment = momentService.findById(id);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/moments")
    MomentResDto create(@RequestBody MomentReqDto momentReqDto){
        return momentService.create(momentReqDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/moments/{id}")
    ResponseEntity<MomentResDto> update(@RequestBody MomentReqDto momentReqDto, @PathVariable Long id){
        var moment = momentService.update(momentReqDto,id);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/moments/{id}")
    ResponseEntity<MomentResDto> delete(@PathVariable Long id){
        var moment = momentService.delete(id);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

//    moments?search=${search}
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/moments", params="search")
    List<MomentResDto> getSearch(@RequestParam String search){
        return momentService.findByDescriptionOrImgUrlOrLocationContaining(search);
    }

    @GetMapping("/users/{id}/moments")
    List<MomentResDto> getUserMoments(@PathVariable Long id){
        return momentService.getUserMoments(id);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/fav-moments")
    List <MomentResDto> getUserFavMoments(){
        return momentService.getUserFavMoments();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/saved-moments")
    List <MomentResDto> getUserSavedMoments(){
        return momentService.getUserSavedMoments();
    }
}


