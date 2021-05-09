package com.flowering.project.board.dto;

import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {

    private Long idx;

    private String content;

    private boolean deleteYn;

    private LocalDateTime createdDate;

    private Board board;

    private String writer;

    public Comment toEntity(){
        Comment build = Comment.builder()
                .idx(idx)
                .content(content)
                .deleteYn(deleteYn)
                .createdDate(createdDate)
                .writer(writer)
                .board(board)
                .build();
        return build;
    }

    @Builder
    public CommentDTO(Long idx, String content, String writer, boolean deleteYn, LocalDateTime createdDate,Board board){
        this.idx=idx;
        this.content=content;
        this.writer=writer;
        this.deleteYn=deleteYn;
        this.createdDate=createdDate;
        this.board=board;
    }
}
