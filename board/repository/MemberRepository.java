package com.flowering.project.board.repository;

import com.flowering.project.board.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>,QuerydslPredicateExecutor<Member> {

    Optional<Member> findByUsername(String username);
}
