package com.example.APISperenza.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.APISperenza.dto.ProductDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.Product;
import com.example.APISperenza.model.Resource;
import com.example.APISperenza.repository.ProductRepository;
import com.example.APISperenza.repository.ResourceRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ResourceRepository resourceRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepositoryRequest,
            ResourceRepository resourceRepositoryRequest,
            ModelMapper modelMapperRequest) {
        this.productRepository = productRepositoryRequest;
        this.modelMapper = modelMapperRequest;
        this.resourceRepository = resourceRepositoryRequest;
    }

    // ------------------------------- CRUD -------------------//
    @Override
    public List<Product> getAll() {

        return productRepository.findAll();
    }

    @Override
    public Product getByIdProduct(long id) {

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le produit avec l'id " + id + " n'existe pas "));
    }

    @Override
    public void saveProduct(Product product) {

        productRepository.save(product);

    }

    @Override
    public Product updateProductByID(long id, Product productRequest) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le produit avec l'id " + id + " n'existe pas "));

        product.setName(productRequest.getName());
        product.setLargeur(productRequest.getLargeur());
        product.setLongueur(productRequest.getLongueur());
        product.setNote(productRequest.getNote());
        product.setPrix(productRequest.getPrix());

        return productRepository.save(product);
    }

    @Override
    public void deleteProductByID(long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Le produit avec l'id " + id + " n'existe pas "));
        productRepository.delete(product);
    }

    @Override
    public void deleteAll() {
        productRepository.deleteAll();

    }

    // ------------------------------- Convert -------------------//
    @Override
    public Product convertToEntity(ProductDTO productDTO) {

        Product product = modelMapper.map(productDTO, Product.class);

        return product;
    }

    @Override
    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
        return productDTO;
    }

    @Override
    public List<Product> convertToListEntity(List<ProductDTO> lDtos) {
        List<Product> list = new ArrayList<>();
        for (ProductDTO productDTO : lDtos) {
            Product product = modelMapper.map(productDTO, Product.class);
            list.add(product);
        }
        return list;
    }

    @Override
    public List<ProductDTO> convertToListDTO(List<Product> lProducts) {

        List<ProductDTO> list = new ArrayList<>();
        for (Product product : lProducts) {
            ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);
            list.add(productDTO);
        }
        return list;
    }

    // supprime les ressource DTO sur les product DTO
    @Override
    public List<ProductDTO> deleteResourceDTO(List<ProductDTO> list) {

        List<ProductDTO> lDtos = new ArrayList<>();

        for (ProductDTO productIterable : list) {

            productIterable.setListResource(null);
            lDtos.add(productIterable);
        }
        return lDtos;
    }
    // --------------------- Product & Resource ----------//

    @Override
    public Product createProductWithResource(Product product) {

        if (product.getResourceForCreate() != null && !product.getResourceForCreate().isEmpty()) {

            List<Product> lProducts = new ArrayList<>();
            lProducts.add(product);
            List<Resource> lResources = new ArrayList<>();

            for (Resource resourceIterable : product.getResourceForCreate()) {

                Resource resource = new Resource();
                resource.setLargeur(resourceIterable.getLargeur());
                resource.setLongueur(resourceIterable.getLongueur());
                resource.setName(resourceIterable.getName());
                resource.setProductsToCreate(lProducts);

                resourceRepository.save(resource);
                lResources.add(resource);
            }
            product.setResourceForCreate(lResources);

        }

        return productRepository.save(product);
    }

}
