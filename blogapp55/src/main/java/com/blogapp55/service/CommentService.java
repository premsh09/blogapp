package com.blogapp55.service;

import com.blogapp55.payload.CommentDto;
import com.blogapp55.payload.FileWithCommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(CommentDto commentDto, long fileId);

    FileWithCommentDto getAllCommentsByFileId(long id);

    CommentDto getCommentById(long id);
}
