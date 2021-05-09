package com.flowering.project.board.repository;

import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface FileRepository extends JpaRepository<File,Long>, QuerydslPredicateExecutor<File> {

}
