package com.example.APISperenza.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.APISperenza.dto.FileDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.File;
import com.example.APISperenza.repository.FileRepository;

@Service
public class FileServiceImpl implements FileService {

    private final ModelMapper modelMapper;

    private final FileRepository fileRepository;

    public FileServiceImpl(ModelMapper modelMapper, FileRepository fileRepository) {
        this.modelMapper = modelMapper;
        this.fileRepository = fileRepository;
    }

    public File createFile(File file) {

        File fileCreated = new File();
        fileCreated.setName(file.getName());
        fileCreated.setUrl(file.getUrl());
        System.out.println("\n file created " + fileCreated.toString());
        return fileRepository.save(fileCreated);
    }

    public List<File> getAll() {
        return fileRepository.findAll();
    }

    @Override
    public File getFileById(long id) {
        // TODO Auto-generated method stub
        return fileRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(
                "Filet not extist with id : " + id));
    }

    // ------------- Convert -------------//

    @Override
    public File convertToEntity(FileDTO fileDTO) {

        File file = modelMapper.map(fileDTO, File.class);
        return file;
    }

    @Override
    public List<File> convertToListEntity(List<FileDTO> lDtos) {

        List<File> list = new ArrayList<>();

        for (FileDTO iterable_element : lDtos) {
            File file = modelMapper.map(iterable_element, File.class);
            list.add(file);
        }
        return list;
    }

    @Override
    public FileDTO convertToDTO(File file) {

        FileDTO fileDTO = modelMapper.map(file, FileDTO.class);

        return fileDTO;
    }

    @Override
    public List<FileDTO> convertToListeDTO(List<File> lFiles) {

        List<FileDTO> list = new ArrayList();

        for (File file : lFiles) {
            FileDTO fileDTO = modelMapper.map(file, FileDTO.class);

            list.add(fileDTO);
        }

        return list;
    }

}
