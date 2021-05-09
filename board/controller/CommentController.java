package com.flowering.project.board.controller;

import com.flowering.project.board.adapter.GsonLocalDateTimeAdapter;
import com.flowering.project.board.domain.Board;
import com.flowering.project.board.domain.Comment;
import com.flowering.project.board.domain.QComment;
import com.flowering.project.board.dto.CommentDTO;
import com.flowering.project.board.repository.BoardRepository;
import com.flowering.project.board.repository.CommentRepository;
import com.google.gson.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
public class CommentController {

    @Autowired
    private BoardRepository boardRepo;

    @Autowired
    private CommentRepository commentRepo;

    @PersistenceContext
    private EntityManager em;

    @PostMapping("/comments/{idx}")
    public JsonObject insertComment(@PathVariable Long idx, @RequestBody final CommentDTO params){
        JsonObject jsonObj = new JsonObject();

        try{
            Board board = boardRepo.findById(idx).get();
            params.setBoard(board);
            params.setCreatedDate(LocalDateTime.now());
            commentRepo.save(params.toEntity());

            jsonObj.addProperty("result",true);
        } catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return jsonObj;
    }

    @PatchMapping("/comments/{commentIdx}/{idx}")
    public JsonObject updateComment(@PathVariable Long commentIdx,@PathVariable Long idx,@RequestBody CommentDTO params){
        JsonObject jsonObj = new JsonObject();
        try{
            Comment comment = commentRepo.findById(idx).get();
            params.setBoard(comment.getBoard());
            params.setCreatedDate(LocalDateTime.now());
            commentRepo.save(params.toEntity());
            jsonObj.addProperty("result",true);
        }catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }
        return jsonObj;

    }

    @GetMapping("/comments/{idx}")
    public JsonObject getCommentList(@PathVariable Long idx,@ModelAttribute("params")CommentDTO params){
        JsonObject jsonObj = new JsonObject();

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);

        QComment qComment = QComment.comment;

        List<Comment> commentList = queryFactory.selectFrom(qComment).where(qComment.board.idx.eq(idx),qComment.deleteYn.eq(false)).orderBy(qComment.idx.desc()).fetch();
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(Comment comment:commentList){
            CommentDTO commentDTO = CommentDTO.builder()
                    .content(comment.getContent())
                    .writer(comment.getWriter())
                    .idx(comment.getIdx())
                    .createdDate(comment.getCreatedDate())
                    .board(comment.getBoard())
                    .build();

            commentDTOList.add(commentDTO);
        }

        if (CollectionUtils.isEmpty(commentDTOList) == false) {

            Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new GsonLocalDateTimeAdapter()).create();
            String data = gson.toJson(commentDTOList);
            JsonArray jsonArray = new JsonParser().parse(data).getAsJsonArray();
            jsonObj.add("commentList", jsonArray);
        }

        return jsonObj;
    }


    @DeleteMapping("/comments/{idx}/{commentIdx}")
    public JsonObject deleteComment(@PathVariable Long idx,@PathVariable Long commentIdx){
        JsonObject jsonObj = new JsonObject();
        try {
            Board board = boardRepo.findById(idx).get();
            Comment comment = commentRepo.findById(commentIdx).get();
            CommentDTO commentDTO = CommentDTO.builder()
                    .idx(comment.getIdx())
                    .content(comment.getContent())
                    .writer(comment.getWriter())
                    .deleteYn(comment.isDeleteYn())
                    .board(comment.getBoard())
                    .createdDate(comment.getCreatedDate())
                    .build();
            commentDTO.setDeleteYn(true);
            commentRepo.save(commentDTO.toEntity());
            jsonObj.addProperty("result",true);
        }catch (DataAccessException e) {
            jsonObj.addProperty("message", "데이터베이스 처리 과정에 문제가 발생하였습니다.");

        } catch (Exception e) {
            jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다.");
        }

        return jsonObj;

    }


}
