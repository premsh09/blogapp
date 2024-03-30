package com.blogapp55.service;

import com.blogapp55.entity.Comment;
import com.blogapp55.entity.File;
import com.blogapp55.exception.ResourceNotFound;
import com.blogapp55.payload.CommentDto;
import com.blogapp55.payload.FileDto;
import com.blogapp55.payload.FileWithCommentDto;
import com.blogapp55.repository.CommentRepository;
import com.blogapp55.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService{

    private FileRepository fileRepository;
    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, long fileId) {
        Optional<File> byId = fileRepository.findById(fileId);
        File file = byId.get();

        Comment comment = mapToEntity(commentDto);
        comment.setFile(file);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedComment);
        return dto;
    }

    @Override
    public FileWithCommentDto getAllCommentsByFileId(long id) {
        File file = fileRepository.findById(id).orElseThrow(()->new ResourceNotFound("Comments not found with id:"+id));
        FileDto dto = new FileDto();
        dto.setId(file.getId());
        dto.setTitle(file.getTitle());
        dto.setDescription(file.getDescription());
        dto.setContent(file.getContent());
        List<Comment> comments = commentRepository.findByFileId(id);
        List<CommentDto> dtos = comments.stream().map(c -> mapToDto(c)).collect(Collectors.toList());
        FileWithCommentDto fileWithCommentDto = new FileWithCommentDto();
        fileWithCommentDto.setCommentDto(dtos);
        fileWithCommentDto.setFile(dto);
        return fileWithCommentDto;
    }

    @Override
    public CommentDto getCommentById(long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFound("Comment not found with id :" + id));
        return mapToDto(comment);
    }

    Comment mapToEntity(CommentDto dto){
        return modelMapper.map(dto, Comment.class);
    }

    CommentDto mapToDto(Comment comment){
        return modelMapper.map(comment, CommentDto.class);
    }
}
