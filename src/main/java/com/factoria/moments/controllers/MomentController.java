package com.factoria.moments.controllers;

import com.factoria.moments.auth.facade.IAuthenticationFacade;
import com.factoria.moments.dtos.moment.MomentReqDto;
import com.factoria.moments.dtos.moment.MomentResDto;
import com.factoria.moments.models.User;
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
    IAuthenticationFacade authenticationFacade;

    public MomentController(IMomentService momentService, IAuthenticationFacade authenticationFacade){
        this.momentService = momentService;
        this.authenticationFacade = authenticationFacade;
    }


    private User getAuth(){
        return authenticationFacade.getAuthUser();
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/moments")
    ResponseEntity<List<MomentResDto>> getAll(){
        User auth = this.getAuth();
        return new ResponseEntity<>(momentService.findAll(auth), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/moments/{id}")
    ResponseEntity<MomentResDto> getById(@PathVariable Long id){
        User auth = this.getAuth();
        var moment = momentService.findById(id, auth);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/moments")
    MomentResDto create(@RequestBody MomentReqDto momentReqDto){
        User auth = this.getAuth();
        return momentService.create(momentReqDto, auth);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/moments/{id}")
    ResponseEntity<MomentResDto> update(@RequestBody MomentReqDto momentReqDto, @PathVariable Long id){
        User auth = this.getAuth();
        var moment = momentService.update(momentReqDto,id, auth);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/moments/{id}")
    ResponseEntity<MomentResDto> delete(@PathVariable Long id){
        User auth = this.getAuth();
        var moment = momentService.delete(id, auth);
        return new ResponseEntity<>(moment, HttpStatus.OK);
    }

//    moments?search=${search}
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value="/moments", params="search")
    List<MomentResDto> getSearch(@RequestParam String search){
        User auth = this.getAuth();
        return momentService.findByDescriptionOrImgUrlOrLocationContaining(search, auth);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/users/{id}/moments")
    List<MomentResDto> getUserMoments(@PathVariable Long id){
        User auth = this.getAuth();
        return momentService.getUserMoments(id,auth);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/fav-moments")
    List <MomentResDto> getUserFavMoments(){
        User auth = this.getAuth();
        return momentService.getUserFavMoments(auth);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/saved-moments")
    List <MomentResDto> getUserSavedMoments(){
        User auth = this.getAuth();
        return momentService.getUserSavedMoments(auth);
    }
}


