package com.example.APISperenza.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.descriptor.web.ResourceBase;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.APISperenza.dto.ResourceDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.Resource;
import com.example.APISperenza.repository.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    private final ModelMapper modelMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ModelMapper modelMapper) {
        this.resourceRepository = resourceRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Resource> getAll() {

        return resourceRepository.findAll();
    }

    @Override
    public Resource getByResourceId(long id) {

        return resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La ressource avec l'id " + id + " n'existe pas "));
    }

    @Override
    public void saveResource(Resource resource) {

        resourceRepository.save(resource);

    }

    @Override
    public Resource updateResourceByI(long id, Resource resource) {

        Resource newResource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La ressource avec l'id " + id + " n'existe pas "));

        newResource.setName(resource.getName());
        newResource.setLargeur(resource.getLargeur());
        newResource.setLongueur(resource.getLongueur());

        return resourceRepository.save(newResource);
    }

    @Override
    public void deleteResourceById(long id) {

        Resource newResource = resourceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La ressource avec l'id " + id + " n'existe pas "));

        resourceRepository.delete(newResource);
    }

    // ----------------------- Convert

    @Override
    public Resource convertToEntity(ResourceDTO resourceDTO) {

        Resource resource = modelMapper.map(resourceDTO, Resource.class);
        return resource;
    }

    @Override
    public ResourceDTO convertToDTO(Resource resource) {
        ResourceDTO resourceDTO = modelMapper.map(resource, ResourceDTO.class);
        return resourceDTO;
    }

    @Override
    public List<Resource> convertToListEntity(List<ResourceDTO> lDtos) {
        List<Resource> list = new ArrayList<>();

        for (ResourceDTO resourceDTO : lDtos) {

            Resource resource = modelMapper.map(resourceDTO, Resource.class);
            list.add(resource);
        }
        return list;
    }

    @Override
    public List<ResourceDTO> convertToListDTO(List<Resource> list) {
        List<ResourceDTO> listDtos = new ArrayList<>();

        for (Resource resource : list) {
            ResourceDTO resourceDTO = modelMapper.map(resource, ResourceDTO.class);
            listDtos.add(resourceDTO);
        }

        return listDtos;
    }

}
