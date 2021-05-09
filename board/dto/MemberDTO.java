package com.flowering.project.board.dto;

import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.Member;
import com.flowering.project.board.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MemberDTO {
    private Long id;
    private String password;
    private String username;
    private Role role;
    private List<Board> boardList;

    public Member toEntity(){
        Member build = Member.builder()
                .id(id)
                .password(password)
                .username(username)
                .role(role)
                .boardList(boardList)
                .build();

        return build;
    }

    @Builder
    public MemberDTO(Long id,String password,String username,Role role,List<Board> boardList){
        this.id=id;
        this.password=password;
        this.username=username;
        this.role=role;
        this.boardList=boardList;
    }
}
