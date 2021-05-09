package com.flowering.project.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String username;

    @Enumerated(EnumType.STRING)
    private Role role;



    @OneToMany(mappedBy = "member",fetch = FetchType.LAZY)
    private List<Board> boardList = new ArrayList<Board>();

    @Builder
    public Member(Long id,String password,String username,Role role,List<Board> boardList) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.role = role;
        this.boardList = boardList;

    }










}
