package com.factoria.moments.repositories;

import com.factoria.moments.models.Moment;

import java.util.List;

public interface IMomentsRepository {
    List<Moment> getMoments();

    Moment getMomentById(Long id);

    List<Moment> getMomentBySearch(String search);
}
