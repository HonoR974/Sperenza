package com.example.APISperenza.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APISperenza.dto.FileDTO;
import com.example.APISperenza.dto.ProductDTO;
import com.example.APISperenza.dto.ResourceDTO;
import com.example.APISperenza.model.File;
import com.example.APISperenza.model.Product;
import com.example.APISperenza.model.Resource;
import com.example.APISperenza.service.FileService;
import com.example.APISperenza.service.ProductService;
import com.example.APISperenza.service.ResourceService;

@CrossOrigin
@RestController
@RequestMapping("/api/resource/")
public class ResourceController {

    private final ResourceService resourceService;

    private final ProductService productService;

    private final FileService fileService;

    public ResourceController(ResourceService resourceService,
            ProductService productService, FileService fileService) {
        this.resourceService = resourceService;
        this.productService = productService;
        this.fileService = fileService;
    }

    // ------------------------ CRUD -------------------------//
    @GetMapping("all")
    public ResponseEntity<List<ResourceDTO>> all() {

        System.out.println("\n get all ");

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

    @DeleteMapping("all")

    public ResponseEntity<String> deleteAll() {

        resourceService.deleteAll();

        return new ResponseEntity<>("Deleted All ", HttpStatus.ACCEPTED);

    }

    // ------------------------ CRUD -------------------------//

    // ------------------------ Resource & Prodcut -------------------------//

    // modifie les ressource d'un produit
    // renvoie un produit avec sa liste de ressource
    @PostMapping("product/{id_product}")
    public ResponseEntity<ProductDTO> resourceForProduct(@PathVariable long id_product,
            @RequestBody ProductDTO productDTO) {
        // produit a changer
        Product product = productService.convertToEntity(productDTO);

        // ressource a ajouter
        List<Resource> lResources = resourceService.convertToListEntity(productDTO.getListResource());

        // produit avec les ressources
        product.setResourceForCreate(lResources);

        // get le produit chang??
        Product product2 = resourceService.updateResourceForProduct(id_product, product);

        ProductDTO productDTOToSend = productService.convertToDTO(product2);

        return new ResponseEntity<>(productDTOToSend, HttpStatus.ACCEPTED);
    }

    // renvoie une liste de produit par l'id ressource
    @GetMapping("findID/{id_resource}")
    public ResponseEntity<ResourceDTO> getProductByResourceId(@PathVariable long id_resource) {
        // verifie si il existe en meme temps
        Resource resource = resourceService.getByResourceId(id_resource);

        List<ProductDTO> lDtos = productService.convertToListDTO(resource.getProductsToCreate());

        // supprime les ressource DTO pour enlever l'effet doublon
        List<ProductDTO> lDtos2 = productService.deleteResourceDTO(lDtos);

        ResourceDTO resourceDTO = resourceService.convertToDTO(resource);
        resourceDTO.setListProduct(lDtos2);

        return new ResponseEntity<>(resourceDTO, HttpStatus.ACCEPTED);
    }

    // ------------------------ Resource & Prodcut -------------------------//

    @PostMapping("create/complete")
    public ResponseEntity<ResourceDTO> createComplete(@RequestBody ResourceDTO resourceDTO) {

        Resource resource = resourceService.convertToEntity(resourceDTO);
        List<File> lFiles = fileService.convertToListEntity(resourceDTO.getLFileDTOs());
        resource.setLFiles(lFiles);

        Resource resourceNew = resourceService.createComptleteResource(resource);
        ResourceDTO resourceDTO2 = resourceService.convertToDTO(resourceNew);
        List<FileDTO> lFileDTOs = fileService.convertToListeDTO(resourceNew.getLFiles());
        resourceDTO2.setLFileDTOs(lFileDTOs);

        return new ResponseEntity<>(resourceDTO2, HttpStatus.CREATED);
    }

}
