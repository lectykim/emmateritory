package com.flowering.project.board.controller;

import com.flowering.project.board.dto.BoardDTO;
import com.flowering.project.board.dto.FileDTO;
import com.flowering.project.board.repository.FileRepository;
import com.flowering.project.board.service.BoardService;
import com.flowering.project.board.service.FileService;
import com.flowering.project.board.util.MD5Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/board/")
public class BoardController {

    @Autowired
    public BoardService boardService;

    @Autowired
    public FileService fileService;

    @Autowired
    public FileRepository fileRepo;

    @GetMapping("/getBoardList")
    public String getBoardList(@ModelAttribute("params")BoardDTO params, Model model){
        List<BoardDTO> boardDTOList =boardService.getBoardList(params);
        model.addAttribute("boardList",boardDTOList);

        return "board/getBoardList";
    }
    @GetMapping("/insertBoard")
    public String insertBoardView(@RequestParam(value = "idx",required = false) Long idx ,Model model){
        if(idx==null){
            model.addAttribute("boardDTO",new BoardDTO());
        } else {
            BoardDTO boardDTO = boardService.getBoard(idx);
            model.addAttribute("boardDTO",boardDTO);
        }
        return "board/insertBoard";
    }

    @PostMapping("/insertBoard")
    public String insertBoard(@ModelAttribute("boardDTO")BoardDTO boardDTO, @RequestParam("file")MultipartFile files,@RequestParam("fileThumbnail")MultipartFile thumbnailFiles){

        Long fieldId;
        List<com.flowering.project.board.domain.File> fileList = new ArrayList<com.flowering.project.board.domain.File>();

        System.out.println("File and thumbnailFile : "+ files.getOriginalFilename() + thumbnailFiles.getOriginalFilename());
        if(files.getOriginalFilename()!=null&&!files.getOriginalFilename().isEmpty()) {
            fieldId = fileSetting(boardDTO, files);
            fileList.add(fileRepo.findById(fieldId).get());
            System.out.println("1 stack");
        }


        if(thumbnailFiles.getOriginalFilename()!=null&&!thumbnailFiles.getOriginalFilename().isEmpty()) {
            fieldId = fileSetting(boardDTO, thumbnailFiles);
            boardDTO.setFieldId(fieldId);
            fileList.add(fileRepo.findById(fieldId).get());
            System.out.println("2 stack");
        }

        if(files.getOriginalFilename().isEmpty()&&thumbnailFiles.getOriginalFilename().isEmpty()){
            boardService.registerBoard(boardDTO);
        }

        boardDTO.setFiles(fileList);
        boardService.registerBoard(boardDTO);

        return "redirect:getBoardList";
    }

    @GetMapping("/getBoard")
    public String getBoard(@RequestParam(value = "idx",required = false) Long idx, Model model){
        BoardDTO boardDTO = boardService.getBoard(idx);
        if(boardDTO.getFieldId()!=null&&boardDTO.getFieldId()>0){
            FileDTO fileDTO = fileService.getFile(boardDTO.getFieldId());
            System.out.println("fileDTO : "+fileDTO);
            model.addAttribute("fileDTO",fileDTO);
        }
        System.out.println("숫자 : "+boardDTO.getFieldId());
        model.addAttribute("boardDTO",boardDTO);



        return "board/getBoard";
    }

    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam(value = "idx") Long idx){
        BoardDTO boardDTO = boardService.getBoard(idx);
        boardDTO.setDeleteYn(true);
        boardService.registerBoard(boardDTO);
        return "forward:getBoardList";
    }



    public Long fileSetting(BoardDTO boardDTO,MultipartFile files){

        try {

            UUID uuid = UUID.randomUUID();

            String origFilename = files.getOriginalFilename();
            String filename = uuid.toString()+"_"+origFilename;
            String savePath = System.getProperty("user.dir");
            savePath = savePath +"\\" + "project" + "\\" + "src" + "\\" + "main" + "\\" +"resources" + "\\" +"static" + "\\" + "img";
            if (!new File(savePath).exists()) {
                try {
                    new File(savePath).mkdir();
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "\\" + filename;
            files.transferTo(new File(filePath));

            FileDTO fileDTO = new FileDTO();
            fileDTO.setOrigFilename(origFilename);
            fileDTO.setFilename(filename);
            fileDTO.setFilePath(filePath);

            Long fieldId = fileService.saveFile(fileDTO);

            return fieldId;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;


}
}

