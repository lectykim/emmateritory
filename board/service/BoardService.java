package com.flowering.project.board.service;

import com.flowering.project.board.dto.BoardDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoardService {


    void registerBoard(BoardDTO boardDTO);


    public List<BoardDTO> getBoardList(BoardDTO params);

    BoardDTO getBoard(Long idx);

    BoardDTO defaultSet(BoardDTO boardDTO);
}
