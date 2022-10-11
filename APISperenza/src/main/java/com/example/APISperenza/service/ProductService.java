package com.example.APISperenza.service;

import java.util.List;

import com.example.APISperenza.dto.ProductDTO;
import com.example.APISperenza.model.Product;

public interface ProductService {

    // CRUD
    List<Product> getAll();

    Product getByIdProduct(long id);

    void saveProduct(Product product);

    Product updateProductByID(long id, Product product);

    void deleteProductByID(Long id);

    // Convert
    Product convertToEntity(ProductDTO productDTO);

    ProductDTO convertToDTO(Product product);

    List<Product> convertToListEntity(List<ProductDTO> lDtos);

    List<ProductDTO> convertToListDTO(List<Product> lProducts);

}
