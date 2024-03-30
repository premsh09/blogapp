package com.blogapp55.contoller;

import com.blogapp55.payload.CommentDto;
import com.blogapp55.payload.FileWithCommentDto;
import com.blogapp55.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/comments")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    // http://localhost:8080/api/comments/1
    @PostMapping("/{fileId}")
    public ResponseEntity<CommentDto> createComment(
            @RequestBody CommentDto commentDto,
            @PathVariable long fileId){
        CommentDto dto = commentService.createComment(commentDto, fileId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/{fileId}")
    public ResponseEntity<FileWithCommentDto> getAllCommentsByFileId(@PathVariable long fileId){
        FileWithCommentDto allCommentsByFileId = commentService.getAllCommentsByFileId(fileId);
        return new ResponseEntity<>(allCommentsByFileId, HttpStatus.OK);
    }

    // http://localhost:8080/api/comments?id=1
    @GetMapping
    public ResponseEntity<CommentDto> getCommentById(@RequestParam long id){
        CommentDto dto = commentService.getCommentById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
