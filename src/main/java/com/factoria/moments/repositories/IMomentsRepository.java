package com.factoria.moments.repositories;

import com.factoria.moments.models.Moment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMomentsRepository extends JpaRepository <Moment, Long> {
    //List<Moment> getMoments();

    //Moment getMomentById(Long id);

    //List<Moment> getMomentBySearch(String search);
}
