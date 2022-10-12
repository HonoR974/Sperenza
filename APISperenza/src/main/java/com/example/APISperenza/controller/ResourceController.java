package com.example.APISperenza.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APISperenza.dto.ResourceDTO;
import com.example.APISperenza.model.Resource;
import com.example.APISperenza.service.ResourceService;

@RestController
@RequestMapping("/api/resource/")
public class ResourceController {

    private final ResourceService resourceService;

    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping("all")
    public ResponseEntity<List<ResourceDTO>> all() {

        List<Resource> list = resourceService.getAll();

        List<ResourceDTO> lDtos = resourceService.convertToListDTO(list);
        return new ResponseEntity<>(lDtos, HttpStatus.ACCEPTED);
    }

    @GetMapping("{id}")
    public ResponseEntity<ResourceDTO> id(@PathVariable long id) {

        Resource resource = resourceService.getByResourceId(id);

        ResourceDTO resourceDTO = resourceService.convertToDTO(resource);

        return new ResponseEntity<>(resourceDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody ResourceDTO resourceDTO) {

        Resource resource = resourceService.convertToEntity(resourceDTO);

        resourceService.saveResource(resource);

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ResourceDTO> update(@PathVariable long id,
            @RequestBody ResourceDTO resourceDTO) {

        Resource resource = resourceService.updateResourceByI(id, resourceService.convertToEntity(resourceDTO));

        ResourceDTO resourceDTO2 = resourceService.convertToDTO(resource);
        return new ResponseEntity<>(resourceDTO2, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {

        resourceService.deleteResourceById(id);

        return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);

    }

}
