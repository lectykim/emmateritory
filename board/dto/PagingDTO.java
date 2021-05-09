package com.flowering.project.board.dto;

import com.flowering.project.board.paging.Criteria;
import com.flowering.project.board.paging.PaginationInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDTO extends Criteria {

    /** 페이징 정보 */
    private PaginationInfo paginationInfo;
}
