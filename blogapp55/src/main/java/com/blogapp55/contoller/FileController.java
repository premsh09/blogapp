package com.blogapp55.contoller;

import com.blogapp55.payload.FileDto;
import com.blogapp55.payload.ListFileDto;
import com.blogapp55.service.FileService;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/file")
public class FileController {


    private FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //  http://localhost:8080/api/file
    @PostMapping
   public ResponseEntity<?> createFile(@Valid @RequestBody FileDto fileDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        FileDto dto = fileService.createFile(fileDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/file/4
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFile(@PathVariable long id){
        fileService.deleteFile(id);
        return new ResponseEntity<>("File deleted!", HttpStatus.OK);
    }

    // http://localhost:8080/api/file?pageNo=0&pageSize=3&sortBy=content&sorDir=desc
    @GetMapping
    public ResponseEntity<ListFileDto> fetchAllFiles(
            @RequestParam(name = "pageNo",defaultValue = "0",required = false)int pageNo,
            @RequestParam(name = "pageSize",defaultValue = "3",required = false)int pageSize,
            @RequestParam(name = "sortBy",defaultValue = "id",required = false)String sortBy,
            @RequestParam(name = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        ListFileDto listFileDto = fileService.fetchAllFiles(pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(listFileDto, HttpStatus.OK);
    }

    // http://localhost:8080/api/file/1
    @GetMapping("/{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable long id){
        FileDto dto = fileService.getFileById(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
