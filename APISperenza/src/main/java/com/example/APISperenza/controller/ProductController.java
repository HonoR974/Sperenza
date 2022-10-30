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

import com.example.APISperenza.dto.ProductDTO;
import com.example.APISperenza.dto.ResourceDTO;
import com.example.APISperenza.model.Product;
import com.example.APISperenza.model.Resource;
import com.example.APISperenza.service.ProductService;
import com.example.APISperenza.service.ResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.*;

@Api("API pour les opérations CRUD sur les produits")
@RestController
@RequestMapping("/api/product/")
public class ProductController {

    private final ProductService productService;

    private final ResourceService resourService;

    public ProductController(ProductService productServiceRequest,
            ResourceService resourceServiceRequest) {
        this.productService = productServiceRequest;
        this.resourService = resourceServiceRequest;
    }

    //decrit la methode 
    @ApiOperation(value = "Récupère touts les produits ")
    @GetMapping("all")
    public ResponseEntity<List<ProductDTO>> all() {

        List<Product> lProducts = productService.getAll();

        List<ProductDTO> lProductDTOs = productService.convertToListDTO(lProducts);

        return new ResponseEntity<>(lProductDTOs, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Récupère un produit selon son ID ")
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved"),
        @ApiResponse(code = 404, message = "Not found - The product with this id was not found ")
      })
    @GetMapping("{id}")
    public ResponseEntity<ProductDTO> id(@PathVariable long id ,@ApiParam(name = "id", value = "Product id", example = "1") long _id) {

        Product product = productService.getByIdProduct(id);

        ProductDTO productDTO = productService.convertToDTO(product);

        return new ResponseEntity<>(productDTO, HttpStatus.ACCEPTED);
    }

    @PostMapping("create")
    public ResponseEntity<String> create(@RequestBody ProductDTO productDTO) {
        Product product = productService.convertToEntity(productDTO);

        productService.saveProduct(product);

        return new ResponseEntity<>("CREATED", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ProductDTO> update(@PathVariable long id,
            @RequestBody ProductDTO productDTO) {
        Product product = productService.convertToEntity(productDTO);

        Product newProduct = productService.updateProductByID(id, product);

        ProductDTO productDTO2 = productService.convertToDTO(newProduct);

        return new ResponseEntity<>(productDTO2, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {

        productService.deleteProductByID(id);

        return new ResponseEntity<>("Deleted", HttpStatus.ACCEPTED);

    }

    @DeleteMapping("all")
    public ResponseEntity<String> deleteAll() {

        productService.deleteAll();

        return new ResponseEntity<>("Deleted All", HttpStatus.ACCEPTED);

    }

    // ------------------------ CRUD -------------------------//

    // ------------------------ Resource & Prodcut -------------------------//

    // Create product with resource
    // return product with ressource
    @PostMapping("create/resource")
    public ResponseEntity<ProductDTO> createproductWithResource(@RequestBody ProductDTO productDTO) {

        Product product1 = productService.convertToEntity(productDTO);
        List<Resource> lResources = resourService.convertToListEntity(productDTO.getListResource());

        product1.setResourceForCreate(lResources);

        Product productFinal = productService.createProductWithResource(product1);

        ProductDTO productDTO2 = productService.convertToDTO(productFinal);
        List<ResourceDTO> lDtos = resourService.convertToListDTO(productFinal.getResourceForCreate());
        productDTO.setListResource(lDtos);

        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

}
