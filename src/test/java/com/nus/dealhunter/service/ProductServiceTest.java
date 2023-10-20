package com.nus.dealhunter.service;

import com.nus.dealhunter.model.Brand;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.payload.request.CreateProductRequest;
import com.nus.dealhunter.repository.BrandRepository;
import com.nus.dealhunter.repository.ProductRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    ProductRepository productRepository;
    @Mock
    BrandRepository brandRepository;
    @InjectMocks
    ProductService productService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);  // Initialize annotated mocks
    }
    @Test
    void testCheckProductNameExists() {
        when(productRepository.existsByProductname(anyString())).thenReturn(Boolean.TRUE);

        Boolean result = productService.checkProductNameExists("productname");
        Assertions.assertEquals(Boolean.TRUE, result);
    }

    @Test
    public void getAllProducts(){
        // Arrange
        List<Product> productList = Arrays.asList(new Product(), new Product());
        Mockito.when(productRepository.findAll()).thenReturn(productList);

        // Act
        List<Product> result = productService.getAllProducts();

        // Assert
        Assert.assertEquals(productList, result);

    }

    @Test
    void testGetProductById() {
        // Arrange
        Long productId = 1L;
        Product mockProduct = new Product(productId, "TestProduct", 29.99);
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        Optional<Product> result = productService.getProductById(productId);

        // Assert
        Assert.assertTrue(result.isPresent());
        Product returnedProduct = result.get();
        Assert.assertEquals(productId,returnedProduct.getId());
        Assert.assertEquals("TestProduct",returnedProduct.getProductname());
        /**0.001 acceptable difference for a double comparison*/
        Assert.assertEquals(29.99,returnedProduct.getCurrentPrice(),0.001);
    }

    @Test
    void testCreateProduct() {
//        // Arrange
//        CreateProductRequest createProductRequest = new CreateProductRequest("productname", "brandname", "storeAddress", "description", "imageUrl", 0d, 0L);
//
//        Product product = productService.createProduct(createProductRequest);
//
//        when(productRepository.save(any(Product.class))).thenReturn(product);
//
//        Product result = productService.createProduct(createProductRequest);
//
//
//        // Assert
//        Assert.assertEquals(result,product);


    }

    @Test
    void testUpdateProduct() {
//        // Arrange
//        Product productToSave = new Product(1L, "NewProduct", 19.99);
//        Brand brand = new Brand(1L, "NewBrand");
//        productToSave.setBrand(brand);
//
//        when(productRepository.save(productToSave)).thenReturn(productToSave);
//        when(brandRepository.findById(brand.getId())).thenReturn(Optional.empty());
//        when(brandRepository.save(brand)).thenReturn(brand);
//
//        // Act
//        Product savedProduct = productService.saveProduct(productToSave);
//
//        // Assert
//        Assert.assertEquals(productToSave, savedProduct);
    }


    @Test
    void testDeleteProduct() {
        productService.deleteProduct(Long.valueOf(1));
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme