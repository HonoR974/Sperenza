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

        try {
            return productRepository.findById(id);

        } catch (Exception exception) {
            throw new ResourceNotFoundException("Le produit avec l'id {0} n'exite pas ", id);

        }

    }

    @Override
    public void saveProduct(Product product) {

        productRepository.save(product);

    }

    @Override
    public Product updateProductByID(long id, Product product) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteProductByID(Long id) {
        // TODO Auto-generated method stub

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
