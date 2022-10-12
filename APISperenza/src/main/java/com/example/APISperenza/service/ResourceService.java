package com.example.APISperenza.service;

import java.util.List;

import com.example.APISperenza.dto.ResourceDTO;
import com.example.APISperenza.model.Resource;

public interface ResourceService {

    // CRUD

    List<Resource> getAll();

    Resource getByResourceId(long id);

    void saveResource(Resource resource);

    Resource updateResourceByI(long id, Resource resource);

    void deleteResourceById(long id);

    // Convert
    Resource convertToEntity(ResourceDTO resourceDTO);

    ResourceDTO convertToDTO(Resource resource);

    List<Resource> convertToListEntity(List<ResourceDTO> lDtos);

    List<ResourceDTO> convertToListDTO(List<Resource> list);
}
