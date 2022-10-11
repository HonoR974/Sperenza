package com.example.APISperenza.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.APISperenza.dto.ProductDTO;
import com.example.APISperenza.model.Product;
import com.example.APISperenza.service.ProductService;

@RestController
@RequestMapping("/api/product/")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productServiceRequest) {
        this.productService = productServiceRequest;
    }

    @GetMapping("all")
    public ResponseEntity<List<ProductDTO>> all() {

        List<Product> lProducts = productService.getAll();

        List<ProductDTO> lProductDTOs = productService.convertToListDTO(lProducts);

        return new ResponseEntity<>(lProductDTOs, HttpStatus.ACCEPTED);
    }

}
