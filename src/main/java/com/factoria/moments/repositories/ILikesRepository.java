package com.factoria.moments.repositories;

import com.factoria.moments.models.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILikesRepository extends JpaRepository<Like, Long> {

    @Query("select l from Like l where l.moment.id = :id")
    List<Like> findByMomentId(@Param("id") Long id);
}
