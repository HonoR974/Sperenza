package com.example.APISperenza.service;


import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.assertj.core.api.Assertions.assertThat;

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
        Product product = new Product();
        Product product2 = new Product();

        List<Product> list = new ArrayList<>();

        list.add(product);
        list.add(product2);

        initMocks(this);

        when(productRepository.findAll()).thenReturn(list);
    }

    @Test
    public void getAllProduct()
    {
        List<Product> list = productServiceImpl.getAll();

        assertThat(list.size()).isEqualTo(2);
    }


}
