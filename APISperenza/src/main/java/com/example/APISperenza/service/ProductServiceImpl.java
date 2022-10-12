package com.example.APISperenza.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.APISperenza.dto.ProductDTO;
import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.Product;
import com.example.APISperenza.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepositoryRequest,
            ModelMapper modelMapperRequest) {
        this.productRepository = productRepositoryRequest;
        this.modelMapper = modelMapperRequest;
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

}
