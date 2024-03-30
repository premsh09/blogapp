package com.blogapp55.service;

import com.blogapp55.payload.FileDto;
import com.blogapp55.payload.ListFileDto;

public interface FileService {


 public FileDto createFile(FileDto fileDto);

 void deleteFile(long id);

 ListFileDto fetchAllFiles(int pageNo, int pageSize, String sortBy, String sortDir);

 FileDto getFileById(long id);
}
