package com.flowering.project.board.repository;

import com.flowering.project.board.domain.Board;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardRepository extends JpaRepository<Board,Long>, QuerydslPredicateExecutor<Board> {

    @Query("SELECT b FROM Board b")
    Page<Board> getBoardList(Pageable pageable);

    @Query("SELECT count(*) FROM Board")
    public Long boardTotalCount(BooleanBuilder builder);
}
