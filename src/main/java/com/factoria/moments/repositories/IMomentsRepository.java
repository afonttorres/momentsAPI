package com.factoria.moments.repositories;

import com.factoria.moments.models.Moment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface IMomentsRepository extends JpaRepository <Moment, Long> {
    @Query("select m from Moment m where m.description like %:search% or m.imgUrl like %:search% or m.location like %:search%")
    List <Moment> findByDescriptionOrImgUrlOrLocationContaining(@Param("search") String search);

    @Query("select m from Moment m where m.creator.id = :id")
    List<Moment> findByUserId(@Param("id") Long id);
}
