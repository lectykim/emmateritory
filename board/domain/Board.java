package com.flowering.project.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class) /* JPA에게 해당 Entity는 Auditiong 기능을 사용함을 알립니다. */
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idx")
    private Long idx;

    @Column(length = 10, nullable = false)
    private String writer;

    @Column(length = 100, nullable = false)
    private String title;

    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @OneToMany(mappedBy = "board",fetch = FetchType.LAZY)
    private List<Comment> commentList = new ArrayList<Comment>();


    @Column(columnDefinition = "boolean not null default false")
    private boolean noticeYn;

    @Column(columnDefinition = "boolean not null default false")
    private boolean secretYn;

    @Column(columnDefinition = "boolean not null default false")
    private boolean deleteYn;

    private Long cnt;

    private String secretPassword;

    @Column(columnDefinition = "default 0")
    private Long fieldId;

    @OneToMany
    @JoinColumn(name = "idx")
    private List<File> files;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;






    @Builder
    public Board(Long idx,String writer,String title,String content,boolean noticeYn,boolean secretYn,boolean deleteYn,boolean likeYn,Long cnt,String secretPassword,LocalDateTime createdDate,LocalDateTime modifiedDate,List<Comment> commentList,Long fieldId,List<File> files,Member member){
        this.idx = idx;
        this.writer =writer;
        this.title = title;
        this.content =content;
        this.noticeYn = noticeYn;
        this.secretYn = secretYn;
        this.deleteYn = deleteYn;
        this.cnt = cnt;
        this.secretPassword = secretPassword;
        this.createdDate=createdDate;
        this.modifiedDate=modifiedDate;
        this.commentList=commentList;
        this.fieldId = fieldId;
        this.files=files;
        this.member=member;
    }
}
