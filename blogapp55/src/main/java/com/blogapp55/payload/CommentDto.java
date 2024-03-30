package com.blogapp55.payload;

import lombok.Data;

@Data
public class CommentDto {
    private long id;
    private String name;
    private String message;

}
