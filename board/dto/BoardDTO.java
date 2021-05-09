package com.flowering.project.board.dto;

import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.File;
import com.flowering.project.board.domain.Member;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoardDTO extends PagingDTO{
    private Long idx;
    private String writer;
    private String content;
    private String title;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private boolean noticeYn;
    private boolean secretYn;
    private boolean deleteYn;
    private boolean likeYn;
    private Long cnt;
    private String secretPassword;
    private Long fieldId;
    private List<File> files;
    private Member member;


    public Board toEntity(){
        Board build = Board.builder()
                .idx(idx)
                .writer(writer)
                .title(title)
                .content(content)
                .noticeYn(noticeYn)
                .secretYn(secretYn)
                .deleteYn(deleteYn)
                .cnt(cnt)
                .secretPassword(secretPassword)
                .createdDate(createdDate)
                .modifiedDate(modifiedDate)
                .fieldId(fieldId)
                .files(files)
                .member(member)
                .build();
        return build;
    }

    @Builder
    public BoardDTO(Long idx,String writer,String title,String content,boolean noticeYn,boolean secretYn,boolean deleteYn,boolean likeYn,Long cnt,String secretPassword, LocalDateTime createdDate, LocalDateTime modifiedDate,Long fieldId,List<File> files,Member member){
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
        this.fieldId=fieldId;
        this.files=files;
        this.member=member;
    }

    public String getFileName(BoardDTO boardDTO){

        if(boardDTO.getFiles().size()==1){
            System.out.println("FileName : " +boardDTO.getFiles().get(0).getFilename());
            return boardDTO.getFiles().get(0).getFilename();
        }else if(boardDTO.getFiles().size()==2){
            System.out.println("FileName : " +boardDTO.getFiles().get(1).getFilename());
            return boardDTO.getFiles().get(1).getFilename();
        }else{
            return "none";
        }
    }


}
