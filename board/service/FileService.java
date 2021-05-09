package com.flowering.project.board.service;

import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.File;
import com.flowering.project.board.dto.BoardDTO;
import com.flowering.project.board.dto.FileDTO;
import com.flowering.project.board.repository.BoardRepository;
import com.flowering.project.board.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class FileService {


    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private FileRepository fileRepo;

    @Transactional
    public Long saveFile(FileDTO fileDTO){
        return fileRepo.save(fileDTO.toEntity()).getId();
    }

    @Transactional
    public FileDTO getFile(Long id){
        File file = fileRepo.findById(id).get();


        FileDTO fileDTO = FileDTO.builder()
                .id(id)
                .origFilename(file.getOrigFilename())
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDTO;
    }


}
