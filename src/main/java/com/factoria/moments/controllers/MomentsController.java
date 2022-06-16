package com.factoria.moments.controllers;

import com.factoria.moments.models.Moment;
import com.factoria.moments.repositories.FakeMomentsRepository;
import com.factoria.moments.repositories.IMomentsRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MomentsController {

    private IMomentsRepository momentsRepository;

    public MomentsController(IMomentsRepository momentsRepository) {
        this.momentsRepository = momentsRepository;
    }

    @GetMapping("/moments")
    List<Moment> getMoments(){
        var momentsRepository = new FakeMomentsRepository();
        var moments = momentsRepository.getMoments();
        return moments;
    }
    //moments/1
    @GetMapping("/moments/{id}")
    Moment getMomentById(@PathVariable Long id){
        var momentsRepository = new FakeMomentsRepository();
        Moment moment = momentsRepository.getMomentById(id);
        return moment;
    }
    ///moments?search={search}
    @GetMapping(value="/moments", params="search")
    List<Moment> getMomentSearch(@RequestParam String search){
        var momentsRepository = new FakeMomentsRepository();
        var searchCollection = momentsRepository.getMomentBySearch(search);
        return searchCollection;
    }

}


