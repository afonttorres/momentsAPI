package com.factoria.moments.controllers;

import com.factoria.moments.dtos.likes.LikeReqDto;
import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.models.Like;
import com.factoria.moments.models.Save;
import com.factoria.moments.models.User;
import com.factoria.moments.services.like.ILikeService;
import com.factoria.moments.services.save.ISaveService;
import com.factoria.moments.services.user.IUserService;
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
    List<Save> getAll(){
        return saveService.getAll();
    }

    @GetMapping("/moments/{id}/saves")
    List<Save> getMomentLikes(@PathVariable Long id){
        return saveService.getMomentSaves(id);
    }

    @PostMapping("/saves")
    String save(@RequestBody SaveReqDto save){
        User auth = this.getAuth(1L);
        return saveService.toggleSave(save, auth);
    }
}