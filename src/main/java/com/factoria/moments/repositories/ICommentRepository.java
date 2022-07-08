package com.factoria.moments.repositories;

import com.factoria.moments.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository <Comment, Long> {
    @Query("select c from Comment c where c.moment.id = :id")
    List<Comment> findByMomentId(@Param("id") Long id);

}
