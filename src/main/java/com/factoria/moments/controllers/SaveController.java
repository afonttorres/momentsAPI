package com.factoria.moments.controllers;

import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.dtos.saves.SaveResDto;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;
import com.factoria.moments.services.save.ISaveService;
import com.factoria.moments.services.user.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SaveController {
    ISaveService saveService;
    IUserService userService;

    public SaveController(ISaveService saveService, IUserService userService){
        this.saveService = saveService;
        this.userService = userService;
    }

    private User getAuth(Long id){
        return userService.findById(id);
    }


    @GetMapping("/saves")
    ResponseEntity<List<SaveResDto>> getAll(){
        var saves = saveService.getAll();
        return new ResponseEntity<>(saves, HttpStatus.OK);
    }

    @GetMapping("/moments/{id}/saves")
    ResponseEntity<List<SaveResDto>> getMomentLikes(@PathVariable Long id){
        var saves = saveService.getMomentSaves(id);
        return new ResponseEntity<>(saves, HttpStatus.OK);
    }

    @PostMapping("/saves")
    ResponseEntity<Boolean> save(@RequestBody SaveReqDto save){
        User auth = this.getAuth(1L);
        var isSaved = saveService.toggleSave(save, auth);
        return new ResponseEntity<>(isSaved, HttpStatus.OK);
    }
}
