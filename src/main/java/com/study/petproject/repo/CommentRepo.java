package com.study.petproject.repo;

import com.study.petproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CommentRepo extends JpaRepository<Comment, Long> {

}
