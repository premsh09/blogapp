package com.blogapp55.payload;

import lombok.Data;

import java.util.List;

@Data
public class FileWithCommentDto {
    private FileDto file;
    private List<CommentDto> commentDto;
}
