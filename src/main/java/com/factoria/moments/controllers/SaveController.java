package com.factoria.moments.controllers;

import com.factoria.moments.dtos.saves.SaveReqDto;
import com.factoria.moments.dtos.saves.SaveResDto;
import com.factoria.moments.services.save.ISaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class SaveController {
    ISaveService saveService;

    public SaveController(ISaveService saveService){
        this.saveService = saveService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
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

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/saves")
    ResponseEntity<Boolean> save(@RequestBody SaveReqDto save){
        var isSaved = saveService.toggleSave(save);
        return new ResponseEntity<>(isSaved, HttpStatus.OK);
    }
}
