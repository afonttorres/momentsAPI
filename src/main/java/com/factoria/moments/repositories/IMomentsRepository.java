package com.factoria.moments.repositories;

import com.factoria.moments.models.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface IMomentsRepository extends JpaRepository <Moment, Long> {
    List<Moment> findByTitle(String search);

}
