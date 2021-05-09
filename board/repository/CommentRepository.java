package com.flowering.project.board.repository;

import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CommentRepository extends JpaRepository<Comment,Long>, QuerydslPredicateExecutor<Comment> {

}
