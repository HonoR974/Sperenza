package com.example.APISperenza.service;


import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.APISperenza.exception.ResourceNotFoundException;
import com.example.APISperenza.model.Product;
import com.example.APISperenza.repository.ProductRepository;

public class ProductServiceImplTest {
    
    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productServiceImpl;

    


    @Before
    public void setUp()
    {
        initMocks(this);
    }

    @Test
    public void getAllProduct()
    {
        
        Product product = new Product();
        Product product2 = new Product();

        List<Product> list = new ArrayList<>();

        list.add(product);
        list.add(product2);

        when(productRepository.findAll()).thenReturn(list);


        List<Product> list2 = productServiceImpl.getAll();

        assertThat(list2.size()).isEqualTo(2);
    }

    @Test
    public void getByIdProduct()
    {
        Product product4 = new Product();
        product4.setName("sac");

        when(productRepository.findById(4).orElseThrow( () -> new ResourceNotFoundException("ce produit n'exte pas "))).thenReturn(product4);
        Product product = productServiceImpl.getByIdProduct(4);

        
        Mockito.verify(productRepository.findById(4));
        assertThat(product.getName()).isEqualTo(product4.getName());
    }

}
