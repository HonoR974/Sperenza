package com.example.APISperenza.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.descriptor.web.ResourceBase;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.APISperenza.dto.ResourceDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.File;
import com.example.APISperenza.model.Product;
import com.example.APISperenza.model.Resource;
import com.example.APISperenza.repository.FileRepository;
import com.example.APISperenza.repository.ProductRepository;
import com.example.APISperenza.repository.ResourceRepository;

@Service
public class ResourceServiceImpl implements ResourceService {

    private final ResourceRepository resourceRepository;

    private final ProductRepository productRepository;

    private final FileRepository fileRepository;

    private final ModelMapper modelMapper;

    public ResourceServiceImpl(ResourceRepository resourceRepositoryReq,
            ModelMapper modelMapper,
            ProductRepository productRepositoryReq,
            FileRepository fileRepositoryReq) {
        this.resourceRepository = resourceRepositoryReq;
        this.modelMapper = modelMapper;
        this.productRepository = productRepositoryReq;
        this.fileRepository = fileRepositoryReq;
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
    public Resource getResourceByName(String name) {
        return resourceRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La ressource avec le nom : " + name + " n'existe pas "));
    }

    @Override
    public void saveResource(Resource resource) {

        resourceRepository.save(resource);

    }

    @Override
    public Resource createComptleteResource(Resource resource) {

        // file
        for (File iterable_File : resource.getLFiles()) {

            List<Resource> lResources = iterable_File.getLResources();

            if (!lResources.contains(resource)) {
                lResources.add(resource);
            }
            iterable_File.setLResources(lResources);
            fileRepository.save(iterable_File);
        }

        // product

        return null;
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

    @Override
    public void deleteAll() {

        resourceRepository.deleteAll();

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

    // --------------------------- Convert----------------//

    // --------------------------- Resource Product ----------------//

    // renvoie un produit avec sa liste de ressource
    // modifie les ressource d'un produit
    @Override
    public Product updateResourceForProduct(long id_product, Product productRequest) {

        Product product = productRepository.findById(id_product)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La ressource avec l'id " + id_product + " n'existe pas "));

        System.out.println("\n productRequest " + productRequest.toString());

        // List produit pour ressource
        List<Product> lProducts = new ArrayList<>();
        lProducts.add(product);

        List<Resource> lResources = new ArrayList<>();

        for (Resource resourceIterable : productRequest.getResourceForCreate()) {

            // ajout du produit sur la ressource
            resourceIterable.setProductsToCreate(lProducts);

            // ajout de la ressource au produit
            lResources.add(resourceIterable);

            resourceRepository.save(resourceIterable);
        }
        product.setResourceForCreate(lResources);

        return productRepository.save(product);
    }

}
