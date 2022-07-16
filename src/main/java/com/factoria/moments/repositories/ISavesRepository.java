package com.factoria.moments.repositories;

import com.factoria.moments.models.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISavesRepository extends JpaRepository <Save, Long> {
    @Query("select s from Save s where s.moment.id = :id")
    List<Save> findByMomentId(@Param("id") Long id);
}
