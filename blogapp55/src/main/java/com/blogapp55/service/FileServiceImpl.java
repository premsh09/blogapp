package com.blogapp55.service;

import com.blogapp55.entity.File;
import com.blogapp55.exception.ResourceNotFound;
import com.blogapp55.payload.FileDto;
import com.blogapp55.payload.ListFileDto;
import com.blogapp55.repository.FileRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService{

    private FileRepository fileRepository;

    private ModelMapper modelMapper;

    public FileServiceImpl(FileRepository fileRepository, ModelMapper modelMapper) {
        this.fileRepository = fileRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public FileDto createFile(FileDto fileDto) {
        File file = mapToEntity(fileDto);

        File savedFile = fileRepository.save(file);

        FileDto dto = mapToDto(savedFile);

        return dto;
    }

    @Override
    public void deleteFile(long id) {
        fileRepository.deleteById(id);
    }

    @Override
    public ListFileDto fetchAllFiles(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortBy).ascending()
                :Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<File> all = fileRepository.findAll(pageable);
        List<File> list = all.getContent();
        List<FileDto> fileDtos = list.stream().map(p -> mapToDto(p)).collect(Collectors.toList());
        ListFileDto listFileDto = new ListFileDto();
        listFileDto.setFileDto(fileDtos);
        listFileDto.setTotalPage(all.getTotalPages());
        listFileDto.setTotalElement((int) all.getTotalElements());
        listFileDto.setPageNumber(all.getNumber());
        listFileDto.setFirstPage(all.isFirst());
        listFileDto.setLastPage(all.isLast());
        return listFileDto;
    }

    @Override
    public FileDto getFileById(long id) {
        File file = fileRepository.findById(id).orElseThrow(() -> new ResourceNotFound("File not found with id: " + id));
        return mapToDto(file);
    }


    File mapToEntity(FileDto fileDto){
        File file = modelMapper.map(fileDto, File.class);
        return file;
    }

    FileDto mapToDto(File file){
        FileDto dto = modelMapper.map(file, FileDto.class);
        return dto;
    }
}
