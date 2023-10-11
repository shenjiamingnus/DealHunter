package com.nus.dealhunter.controller;


import com.nus.dealhunter.model.PriceHistory;
import com.nus.dealhunter.model.Product;
import com.nus.dealhunter.repository.ProductRepository;
import com.nus.dealhunter.service.ProductService;
import com.nus.dealhunter.service.PriceHistoryService;
import com.nus.dealhunter.util.JwtTokenUtil;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    ProductService productService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductRepository priceHistoryRepository;

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
        when(productService.getAllProducts()).thenReturn(List.of(new Product(Long.valueOf(1), "productname", 0d)));

        List<Product> result = productController.getAllProducts();
        Assertions.assertEquals(List.of(new Product(Long.valueOf(1), "productname", 0d)), result);
    }

    @Test
    void testGetProductByProductname() {
        when(productService.getProductByProductname(anyString())).thenReturn(List.of(new Product(Long.valueOf(1), "productname", 0d)));

        ResponseEntity<List<Product>> result = productController.getProductByProductname("productname");
        Assertions.assertEquals(List.of(new Product(Long.valueOf(1), "productname", 0d)), result.getBody());
    }

    @Test
    void testGetProductById() {
        when(productService.getProductById(anyLong())).thenReturn(Optional.of(new Product(Long.valueOf(1), "productname", 0d)));

        ResponseEntity<Product> result = productController.getProductById(Long.valueOf(1));
        Product product = Optional.of(new Product(Long.valueOf(1), "productname", 0d)).get();
        Assertions.assertEquals(product, result.getBody());
    }

    @Test
    void testCreateProduct() {
        when(productService.saveProduct(any())).thenReturn(new Product(Long.valueOf(1), "productname", 0d));

        ResponseEntity<Product> result = productController.createProduct(new Product(Long.valueOf(1), "productname", 0d));
        Product product = Optional.of(new Product(Long.valueOf(1), "productname", 0d)).get();
        Assertions.assertEquals(product, result.getBody());
    }

    @Test
    void testDeleteProduct() {
        ResponseEntity<Void> result = productController.deleteProduct(Long.valueOf(1));
        Assertions.assertEquals(null, result.getBody());
    }

    @Test
    void testGetPriceHistory() {
        when(productService.getProductPriceHistory(anyString(), anyString())).thenReturn(List.of(new PriceHistory(0d, LocalDate.of(2023, Month.OCTOBER, 11), new Product(Long.valueOf(1), "productname", 0d))));

        ResponseEntity<List<PriceHistory>> result = productController.getPriceHistory("productname", "brandname");
        List<PriceHistory> priceHistory = List.of(new  PriceHistory(0d, LocalDate.of(2023, Month.OCTOBER, 11), new Product(Long.valueOf(1), "productname", 0d)));
        Assertions.assertEquals(priceHistory, result.getBody());
    }

    @Test
    void testSubmitNewPrice() {
        when(productService.submitNewPrice(anyString(), anyString(), anyDouble())).thenReturn(new Product(Long.valueOf(1), "productname", 1d));

        ResponseEntity<Product> result = productController.submitNewPrice("productname", "brandname", 0d);
        Product products = new Product(Long.valueOf(1), "productname", 1d);
        Assertions.assertEquals(products, result.getBody());
    }
}








//Generated with love by TestMe :) Please report issues and submit feature requests at: https://weirddev.com/forum#!/testme