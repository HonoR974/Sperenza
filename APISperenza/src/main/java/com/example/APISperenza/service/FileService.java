package com.example.APISperenza.service;

import java.util.List;

import com.example.APISperenza.dto.FileDTO;
import com.example.APISperenza.model.File;

public interface FileService {
    File createFile(File file);

    List<File> getAll();

    File getFileById(long id);

    // convert
    File convertToEntity(FileDTO fileDTO);

    List<File> convertToListEntity(List<FileDTO> lDtos);

    FileDTO convertToDTO(File file);

    List<FileDTO> convertToListeDTO(List<File> lFiles);
}
