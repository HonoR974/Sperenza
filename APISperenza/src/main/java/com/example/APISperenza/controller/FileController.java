package com.example.APISperenza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APISperenza.dto.FileDTO;
import com.example.APISperenza.model.File;
import com.example.APISperenza.service.FileService;

@CrossOrigin
@RestController
@RequestMapping("/api/file/")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping()
    public ResponseEntity<FileDTO> createFile(@RequestBody FileDTO fileDTO) {
        File file = fileService.convertToEntity(fileDTO);
        System.out.println(("\n create file " + file));
        File newFile = fileService.createFile(file);
        FileDTO fDto = fileService.convertToDTO(newFile);

        return new ResponseEntity<>(fDto, HttpStatus.ACCEPTED);
    }

    @GetMapping()
    public ResponseEntity<List<FileDTO>> getAllFile() {
        List<FileDTO> list = fileService.convertToListeDTO(fileService.getAll());
        return new ResponseEntity<>(list, HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable long id) {
        FileDTO fileDTO = fileService.convertToDTO(fileService.getFileById(id));
        return ResponseEntity.ok(fileDTO);
    }

}