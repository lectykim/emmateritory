package com.flowering.project.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD",nullable = false)
    private Board board;

    private String writer;

    @Column(columnDefinition = "boolean not null default false")
    private boolean deleteYn;

    private LocalDateTime createdDate;

    @Builder
    public Comment(Long idx,String content,Board board,String writer,boolean deleteYn,LocalDateTime createdDate){
        this.idx=idx;
        this.content=content;
        this.board=board;
        this.writer=writer;
        this.deleteYn=deleteYn;
        this.createdDate=createdDate;
    }
}
