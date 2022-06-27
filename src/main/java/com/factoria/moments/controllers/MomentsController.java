package com.factoria.moments.controllers;

import com.factoria.moments.models.Moment;
import com.factoria.moments.repositories.IMomentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="http://localhost:3000/")
public class MomentsController {

    private IMomentsRepository momentsRepository;

    @Autowired
    public MomentsController(IMomentsRepository momentsRepository) {
        this.momentsRepository = momentsRepository;
    }

    @GetMapping("/moments")
    List<Moment> getMoments(){
        var moments = this.momentsRepository.findAll();
        return moments;
    }
    //moments/1
    @GetMapping("/moments/{id}")
    Moment getMomentById(@PathVariable Long id){
        var moment = this.momentsRepository.findById(id).get();
        return moment;
    }
    @PostMapping("/moments")
    Moment createMoment(@RequestBody Moment newMoment){
        var moment = this.momentsRepository.save(newMoment);
        return moment;
    }
    @PutMapping("/moments/{id}")
    Moment updateMoment(@PathVariable Long id, @RequestBody Moment momentData){
        Moment moment = this.momentsRepository.findById(id).get();
        moment.setLocation(momentData.getLocation());;
        moment.setDescription(momentData.getDescription());
        moment.setImgUrl(momentData.getImgUrl());
        final Moment updatedMoment = this.momentsRepository.save(moment);
        return updatedMoment;
    }

    @PatchMapping("/moments/{id}/{action}")
    Moment saveMoment(@PathVariable Long id, @PathVariable String action){
        Moment moment = this.momentsRepository.findById(id).get();
        if(action.equals("like")){ moment.setLiked(!moment.isLiked());}
        if(action.equals("save")){ moment.setSaved(!moment.isSaved());}
        final Moment updatedMoment = this.momentsRepository.save(moment);
        return updatedMoment;
    }

//    moments?search={search}
//   @GetMapping(value="/moments", params="search")
//    List<Moment> getMomentSearch(@RequestParam String search){
//        var searchCollection = this.momentsRepository.findByDescription(search);
//        return searchCollection;
//    }

    @GetMapping(value="/moments", params="search")
    List<Moment> getSearch(@RequestParam String search){
        var searchCollection = this.momentsRepository.findByDescriptionOrImgUrlOrLocationContaining(search);
        return searchCollection;
    }

    @DeleteMapping("/moments/{id}")
    boolean deleteMoment(@PathVariable Long id) {
        Moment moment = this.momentsRepository.findById(id).get();
        this.momentsRepository.delete(moment);
        return true;
    }
}


