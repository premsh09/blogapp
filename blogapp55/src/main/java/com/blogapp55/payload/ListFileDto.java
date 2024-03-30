package com.blogapp55.payload;

import lombok.Data;

import java.util.List;
@Data
public class ListFileDto {
    private List<FileDto> fileDto;
    private int totalPage;
    private int totalElement;
    private int pageNumber;
    private boolean firstPage;
    private boolean lastPage;
}
