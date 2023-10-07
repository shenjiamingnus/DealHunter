package com.nus.dealhunter.controller;

import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.util.JwtTokenUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

class ProductControllerTest {
    @Mock
    ProductService productService;
    @Mock
    JwtTokenUtil jwtTokenUtil;
    @InjectMocks
    ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllProducts() {
        when(productService.getAllProducts()).thenReturn(List.of(new Product("productname", "brandname")));

        List<Product> result = productController.getAllProducts();
        Assertions.assertEquals(List.of(new Product("productname", "brandname")), result);
    }

    @Test
    void testGetProductById() {

        // Arrange
        Long productId = 1L;
        Product product = new Product();
        Mockito.when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        // Act
        ResponseEntity<Product> result = productController.getProductById(productId);

        // Assert
        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
        Assert.assertEquals(product, result.getBody());
    }

    @Test
    void testCreateProduct() {
        when(productService.saveProduct(any())).thenReturn(new Product("productname", "brandname"));

        ResponseEntity<Product> result = productController.createProduct(new Product("productname", "brandname"));
        Assertions.assertNotEquals(null, result);
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        Long productId = 1L;

        // Act
        ResponseEntity<Void> result = productController.deleteProduct(productId);

        // Assert
        Assert.assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        Mockito.verify(productService).deleteProduct(productId);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme