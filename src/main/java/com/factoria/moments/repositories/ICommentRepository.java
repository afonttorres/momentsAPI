package com.factoria.moments.repositories;

import com.factoria.moments.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICommentRepository extends JpaRepository <Comment, Long> {
}
