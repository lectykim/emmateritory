package com.flowering.project.board.service;

import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.QBoard;
import com.flowering.project.board.dto.BoardDTO;
import com.flowering.project.board.paging.PaginationInfo;
import com.flowering.project.board.repository.BoardRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardRepository boardRepo;

    @Transactional
    public void registerBoard(BoardDTO boardDTO){
        boardDTO.setWriter("관리자");
        if(boardDTO.getIdx()==null){
            if(boardDTO.getCnt()==null) {
                boardDTO.setCnt(0L);
            }
            boardDTO.setCreatedDate(LocalDateTime.now());

        }else{
            boardDTO.setModifiedDate(LocalDateTime.now());
        }

        if(boardDTO.getFieldId()==null){
            boardDTO.setFieldId(0L);
        }

        boardRepo.save(boardDTO.toEntity());
    }

    @Transactional
    public List<BoardDTO> getBoardList(BoardDTO params){
        BooleanBuilder builder = new BooleanBuilder();

        QBoard qBoard = QBoard.board;
        builder.and(qBoard.deleteYn.eq(false));
        builder.and(qBoard.noticeYn.eq(false));

        System.out.println("SearchType : "+params.getSearchType());
        System.out.println("SearchKeyWord : "+params.getSearchType());
        if(params.getSearchType()==null){
            params.setSearchType("dummy");
        }
        if(params.getSearchType().equals("title")){
            builder.and(qBoard.title.like("%"+params.getSearchKeyword()+"%"));
        }
        if(params.getSearchType().equals("content")){
            builder.and(qBoard.content.like("%"+params.getSearchKeyword()+"%"));
        }
        if(params.getSearchType().equals("writer")){
            builder.and(qBoard.writer.like("%"+params.getSearchKeyword()+"%"));
        }
        if(params.getSearchType().equals("")){
            builder.and(qBoard.title.like("%"+params.getSearchKeyword()+"%").or(qBoard.content.like("%"+params.getSearchKeyword()+"%")).or(qBoard.writer.like("%"+params.getSearchKeyword()+"%")));
        }



        PaginationInfo paginationInfo = new PaginationInfo(params);
        paginationInfo.setTotalRecordCount((int)totalCount(builder));

        params.setPaginationInfo(paginationInfo);

        Pageable pageable = PageRequest.of(params.getPaginationInfo().getCriteria().getCurrentPageNo()-1,16, Sort.by(new Sort.Order(Sort.Direction.DESC,"idx"),new Sort.Order(Sort.Direction.DESC,"createdDate")));
        Page<Board> page = boardRepo.findAll(builder,pageable);
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for(Board board:page){
            BoardDTO boardDTO = BoardDTO.builder()
                    .idx(board.getIdx())
                    .writer(board.getWriter())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .fieldId(board.getFieldId())
                    .files(board.getFiles())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return boardDTOList;
    }

    @Transactional
    public BoardDTO getBoard(Long idx){
        Board board = boardRepo.findById(idx).get();
        BoardDTO boardDTO = BoardDTO.builder()
                .idx(board.getIdx())
                .writer(board.getWriter())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .noticeYn(board.isNoticeYn())
                .secretYn(board.isSecretYn())
                .secretPassword(board.getSecretPassword())
                .createdDate(board.getCreatedDate())
                .modifiedDate(board.getModifiedDate())
                .fieldId(board.getFieldId())
                .build();
        return boardDTO;

    }
    public long totalCount(BooleanBuilder builder){


        return boardRepo.boardTotalCount(builder);
    }

    public BoardDTO defaultSet(BoardDTO boardDTO){
        boardDTO.setWriter("관리자");
        boardDTO.setCnt(0L);
        boardDTO.setCreatedDate(LocalDateTime.now());
        boardDTO.setFieldId(0L);
        return boardDTO;
    }




}
